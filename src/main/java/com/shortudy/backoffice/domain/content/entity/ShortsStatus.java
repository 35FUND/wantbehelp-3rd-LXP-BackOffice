package com.shortudy.backoffice.domain.content.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 숏츠 상태 열거형
 */
@Getter
@RequiredArgsConstructor
public enum ShortsStatus {
    // 1,2차 검수 반려
    REJECT("반려"),
    // 2차 검수 완료 후 게시
    PUBLISHED("게시"),
    // 1차 검수 완료(AI 점검)
    AI_CHECK("AI 점검"),
    // 1차 검수 전
    PENDING("대기");

    private final String description;
}
