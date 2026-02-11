package com.shortudy.backoffice.domain.content.dto.response;

import com.shortudy.backoffice.domain.content.entity.Category;

/**
 * 카테고리 목록 응답 DTO
 */
public record CategoryListResponse(
        Long id,
        String name
) {
    public static CategoryListResponse from(Category category) {
        return new CategoryListResponse(category.getId(), category.getName());
    }
}
