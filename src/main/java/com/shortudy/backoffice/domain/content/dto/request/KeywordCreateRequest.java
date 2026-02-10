package com.shortudy.backoffice.domain.content.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

/**
 * 키워드 생성 요청 DTO
 */
@Getter
public class KeywordCreateRequest {
    @NotBlank
    private String displayName;
}
