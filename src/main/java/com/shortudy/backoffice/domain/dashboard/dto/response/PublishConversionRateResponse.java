package com.shortudy.backoffice.domain.dashboard.dto.response;

import lombok.Builder;
import lombok.Getter;

/**
 * 게시 전환율 응답 DTO
 */
@Getter
@Builder
public class PublishConversionRateResponse {
    // 전체 숏츠 수
    private long totalCount;
    // 게시(PUBLISHED) 상태 숏츠 수
    private long publishedCount;
    // 게시 전환율 (publishedCount / totalCount * 100)
    private double conversionRate;
}
