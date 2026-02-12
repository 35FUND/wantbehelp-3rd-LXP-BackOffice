package com.shortudy.backoffice.domain.content.dto.response;

import com.shortudy.backoffice.domain.content.entity.Shorts;
import com.shortudy.backoffice.domain.content.entity.ShortsInspectionResult;
import com.shortudy.backoffice.domain.content.entity.ShortsStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 백오피스 영상 검수 목록 응답 DTO
 */
@Getter
@Builder
public class ShortsReviewItemResponse {
    private Long shortsId;
    private Long userId;
    private String authorName;
    private Long categoryId;
    private String title;
    private ShortsStatus status;
    private String shortsStatusDescription;
    private String videoUrl;
    private LocalDateTime createdAt;
    private ShortsInspectionResultResponse inspectionResult;

    public static ShortsReviewItemResponse of(Shorts shorts, String authorName, ShortsInspectionResult latestResult) {
        return ShortsReviewItemResponse.builder()
                .shortsId(shorts.getId())
                .userId(shorts.getUserId())
                .authorName(authorName)
                .categoryId(shorts.getCategoryId())
                .title(shorts.getTitle())
                .status(shorts.getStatus())
                .shortsStatusDescription(resolveShortsStatusDescription(shorts.getStatus(), latestResult))
                .videoUrl(shorts.getVideoUrl())
                .createdAt(shorts.getCreatedAt())
                .inspectionResult(latestResult == null ? null : ShortsInspectionResultResponse.from(latestResult))
                .build();
    }

    private static String resolveShortsStatusDescription(ShortsStatus status, ShortsInspectionResult latestResult) {
        if (status == ShortsStatus.REJECT) {
            String rejectReason = latestResult == null ? null : latestResult.getReason();
            return (rejectReason == null || rejectReason.isBlank()) ? status.getDescription() : rejectReason;
        }

        if (status == ShortsStatus.AI_CHECK) {
            return "AI심사";
        }

        return status.getDescription();
    }
}
