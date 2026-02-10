package com.shortudy.backoffice.domain.dashboard.dto.response;

import com.shortudy.backoffice.domain.dashboard.dto.projection.CategoryShortsCountView;
import lombok.Builder;
import lombok.Getter;

/**
 * 카테고리별 쇼츠 수 응답 DTO
 */
@Getter
@Builder
public class CategoryShortsCountResponse {
    private Long categoryId;
    private String categoryName;
    private Long shortsCount;

    public static CategoryShortsCountResponse from(CategoryShortsCountView view) {
        return CategoryShortsCountResponse.builder()
                .categoryId(view.getCategoryId())
                .categoryName(view.getCategoryName())
                .shortsCount(view.getShortsCount())
                .build();
    }
}
