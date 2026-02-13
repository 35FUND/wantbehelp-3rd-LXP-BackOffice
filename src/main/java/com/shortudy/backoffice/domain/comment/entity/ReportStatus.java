package com.shortudy.backoffice.domain.comment.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 신고 처리 상태 열거형
 */
@Getter
@RequiredArgsConstructor
public enum ReportStatus {
    PENDING("대기중"),
    PROCESSED("통과"),
    REJECTED("삭제");

    private final String description;
}
