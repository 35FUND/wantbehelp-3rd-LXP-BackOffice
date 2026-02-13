package com.shortudy.backoffice.domain.content.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 숏츠 반려 사유 열거형
 */
@Getter
@RequiredArgsConstructor
public enum ShortsRejectReason {
    POLICY_VIOLATION("운영정책 위반"),
    COPYRIGHT("저작권 침해 우려"),
    SPAM("도배/광고성 콘텐츠"),
    LOW_QUALITY("저화질/품질 미달"),
    ETC("기타");

    private final String description;
}
