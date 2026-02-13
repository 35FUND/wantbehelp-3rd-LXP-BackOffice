package com.shortudy.backoffice.domain.comment.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 댓글 삭제 사유 열거형
 */
@Getter
@RequiredArgsConstructor
public enum CommentDeleteReason {
    ABUSE("욕설/비하 표현"),
    SEXUAL("음란/선정성"),
    PERSONAL_INFO("개인정보 노출"),
    SPAM("도배/광고"),
    ETC("기타 운영정책 위반");

    private final String description;
}
