package com.shortudy.backoffice.domain.content.dto.response;

import lombok.Builder;
import lombok.Getter;

/**
 * AI 검수 트리거 응답 DTO
 */
@Getter
@Builder
public class InspectionTriggerResponse {
    private Long shortsId;
    private String inspectionStatus;
    private String message;
}
