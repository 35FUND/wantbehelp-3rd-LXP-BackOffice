package com.shortudy.backoffice.domain.content.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * AI Agent 검수 결과 data 필드
 */
@Getter
@Setter
public class AiInspectorDataResponse {
    @JsonProperty("is_it_education")
    private Boolean isItEducation;

    @JsonProperty("confidence_score")
    private Double confidenceScore;

    private String category;
    private String reason;
}
