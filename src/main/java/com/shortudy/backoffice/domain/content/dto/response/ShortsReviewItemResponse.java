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
                .videoUrl(shorts.getVideoUrl())
                .createdAt(shorts.getCreatedAt())
                .inspectionResult(latestResult == null ? null : ShortsInspectionResultResponse.from(latestResult))
                .build();
    }
}
