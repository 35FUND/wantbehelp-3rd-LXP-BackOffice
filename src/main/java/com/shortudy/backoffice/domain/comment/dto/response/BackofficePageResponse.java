package com.shortudy.backoffice.domain.comment.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * 백오피스 페이징 공통 응답 DTO
 */
@Getter
@Builder
public class BackofficePageResponse<T> {
    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean first;
    private boolean last;
}
