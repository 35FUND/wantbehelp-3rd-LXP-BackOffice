package com.shortudy.backoffice.domain.content.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * 백오피스 영상 검수 목록 페이지 응답 DTO
 */
@Getter
@Builder
public class ShortsReviewPageResponse {
    private List<ShortsReviewItemResponse> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean first;
    private boolean last;
}
