package com.shortudy.backoffice.domain.content.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * AI Agent inspect API 응답 DTO
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AiInspectorResponse {
    private String status;
    private AiInspectorDataResponse data;
    private String message;
}
