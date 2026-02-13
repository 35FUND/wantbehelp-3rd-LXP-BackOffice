package com.shortudy.backoffice.domain.content.service;

import com.shortudy.backoffice.domain.content.dto.response.AiInspectorDataResponse;
import com.shortudy.backoffice.domain.content.dto.response.AiInspectorResponse;
import com.shortudy.backoffice.domain.content.dto.response.InspectionTriggerResponse;
import com.shortudy.backoffice.domain.content.dto.response.ShortsInspectionResultResponse;
import com.shortudy.backoffice.domain.content.dto.response.ShortsReviewItemResponse;
import com.shortudy.backoffice.domain.content.dto.response.ShortsReviewPageResponse;
import com.shortudy.backoffice.domain.content.entity.Shorts;
import com.shortudy.backoffice.domain.content.entity.ShortsInspectionResult;
import com.shortudy.backoffice.domain.content.entity.ShortsRejectReason;
import com.shortudy.backoffice.domain.content.entity.ShortsStatus;
import com.shortudy.backoffice.domain.content.repository.ShortsInspectionResultRepository;
import com.shortudy.backoffice.domain.content.repository.ShortsRepository;
import com.shortudy.backoffice.domain.user.entity.User;
import com.shortudy.backoffice.domain.user.repository.UserRepository;
import com.shortudy.backoffice.global.error.BaseException;
import com.shortudy.backoffice.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 백오피스 영상 검수 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShortsReviewService {

    private final ShortsRepository shortsRepository;
    private final ShortsInspectionResultRepository shortsInspectionResultRepository;
    private final UserRepository userRepository;
    private final AiInspectorClient aiInspectorClient;

    public ShortsReviewPageResponse getReviewItems(ShortsStatus status, Pageable pageable) {
        Page<Shorts> page = shortsRepository.findByStatusFilter(status, pageable);

        List<Long> shortsIds = page.getContent().stream().map(Shorts::getId).toList();
        List<Long> userIds = page.getContent().stream().map(Shorts::getUserId).distinct().toList();

        Map<Long, String> authorNameByUserId = userRepository.findByIdIn(userIds)
                .stream()
                .collect(Collectors.toMap(User::getId, User::getNickname));

        Map<Long, ShortsInspectionResult> latestByShortsId = shortsInspectionResultRepository.findByShortsIdIn(shortsIds)
                .stream()
                .collect(Collectors.toMap(
                        ShortsInspectionResult::getShortsId,
                        Function.identity(),
                        (a, b) -> a.getCreatedAt().isAfter(b.getCreatedAt()) ? a : b
                ));

        List<ShortsReviewItemResponse> content = page.getContent().stream()
                .map(shorts -> ShortsReviewItemResponse.of(
                        shorts,
                        authorNameByUserId.get(shorts.getUserId()),
                        latestByShortsId.get(shorts.getId())
                ))
                .toList();

        return ShortsReviewPageResponse.builder()
                .content(content)
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .build();
    }

    public ShortsInspectionResultResponse getLatestInspectionResult(Long shortsId) {
        ShortsInspectionResult result = shortsInspectionResultRepository.findTopByShortsIdOrderByCreatedAtDesc(shortsId)
                .orElseThrow(() -> new BaseException(ErrorCode.CONTENT_NOT_FOUND));
        return ShortsInspectionResultResponse.from(result);
    }

    /**
     * 임시 자동 검수 폴링 대상 숏츠 ID 목록을 조회한다.
     */
    public List<Long> findPendingInspectionTargets(int limit) {
        if (limit <= 0) {
            return List.of();
        }

        Pageable pageable = PageRequest.of(0, limit);
        return shortsRepository.findByStatusAndVideoUrlIsNotNullOrderByIdAsc(ShortsStatus.PENDING, pageable)
                .stream()
                .map(Shorts::getId)
                .toList();
    }

    /**
     * 임시 폴링 정책: AI 호출 실패 시 재시도 무한 루프를 방지하기 위해
     * PENDING 대상을 AI_CHECK로 전환해 큐에서 제외한다.
     */
    @Transactional
    public void markAsAiCheckAfterFailedInspection(Long shortsId) {
        shortsRepository.findById(shortsId)
                .ifPresent(shorts -> {
                    if (shorts.getStatus() == ShortsStatus.PENDING) {
                        shorts.markAiCheck();
                    }
                });
    }

    @Transactional
    public InspectionTriggerResponse triggerInspection(Long shortsId) {
        Shorts shorts = shortsRepository.findById(shortsId)
                .orElseThrow(() -> new BaseException(ErrorCode.CONTENT_NOT_FOUND));

        shorts.markAiCheck();

        AiInspectorResponse response = aiInspectorClient.inspect(shortsId);
        AiInspectorDataResponse data = response == null ? null : response.getData();
        if (data != null) {
            saveInspectionResult(shortsId, response.getStatus(), data);
        }

        return InspectionTriggerResponse.builder()
                .shortsId(shortsId)
                .inspectionStatus(response == null ? "UNKNOWN" : response.getStatus())
                .message(response == null ? "AI 응답이 비어 있습니다." : response.getMessage())
                .build();
    }

    @Transactional
    public void updateStatus(Long shortsId, ShortsStatus status, ShortsRejectReason rejectReason) {
        Shorts shorts = shortsRepository.findById(shortsId)
                .orElseThrow(() -> new BaseException(ErrorCode.CONTENT_NOT_FOUND));

        if (status == ShortsStatus.REJECT && rejectReason == null) {
            throw new BaseException(ErrorCode.CONTENT_REJECT_REASON_REQUIRED);
        }

        shorts.changeStatus(status, rejectReason);
    }

    @Transactional
    protected void saveInspectionResult(Long shortsId, String inspectionStatus, AiInspectorDataResponse data) {
        ShortsInspectionResult result = ShortsInspectionResult.builder()
                .shortsId(shortsId)
                .inspectionStatus(inspectionStatus == null ? "UNKNOWN" : inspectionStatus)
                .category(data.getCategory())
                .confidenceScore(data.getConfidenceScore())
                .reason(data.getReason())
                .createdAt(LocalDateTime.now())
                .build();
        shortsInspectionResultRepository.save(result);
    }
}
