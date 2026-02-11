package com.shortudy.backoffice.domain.content.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

/**
 * 카테고리 생성 요청 DTO
 */
@Getter
public class CategoryCreateRequest {
    @NotBlank
    private String name;
}
