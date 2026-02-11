package com.shortudy.backoffice.domain.content.dto.response;

import com.shortudy.backoffice.domain.content.entity.Keyword;

/**
 * 키워드 목록 응답 DTO
 */
public record KeywordListResponse(
        Long id,
        String name
) {
    public static KeywordListResponse from(Keyword keyword) {
        return new KeywordListResponse(keyword.getId(), keyword.getDisplayName());
    }
}
