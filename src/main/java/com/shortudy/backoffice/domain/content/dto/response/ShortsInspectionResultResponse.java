package com.shortudy.backoffice.domain.content.dto.response;

import com.shortudy.backoffice.domain.content.entity.ShortsInspectionResult;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 쇼츠 AI 검수 결과 응답 DTO
 */
@Getter
@Builder
public class ShortsInspectionResultResponse {
    private String inspectionStatus;
    private Boolean isItEducation;
    private String category;
    private Double confidenceScore;
    private String reason;
    private LocalDateTime inspectedAt;

    public static ShortsInspectionResultResponse from(ShortsInspectionResult result) {
        return ShortsInspectionResultResponse.builder()
                .inspectionStatus(result.getInspectionStatus())
                .isItEducation("Approved".equalsIgnoreCase(result.getInspectionStatus()))
                .category(result.getCategory())
                .confidenceScore(result.getConfidenceScore())
                .reason(result.getReason())
                .inspectedAt(result.getCreatedAt())
                .build();
    }
}
