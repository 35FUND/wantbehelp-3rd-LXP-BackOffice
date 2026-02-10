package com.shortudy.backoffice.domain.dashboard.dto;

/**
 * 카테고리별 쇼츠 수 조회 Projection
 */
public interface CategoryShortsCountView {
    Long getCategoryId();
    String getCategoryName();
    Long getShortsCount();
}
