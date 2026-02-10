package com.shortudy.backoffice.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 전역 에러 코드 정의
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // Common
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001", " 올바르지 않은 입력값입니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C002", " 지원하지 않는 HTTP 메서드입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C003", " 서버 내부 오류가 발생했습니다."),
    
    // Domain Specific
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "M001", " 사용자를 찾을 수 없습니다."),
    CONTENT_NOT_FOUND(HttpStatus.NOT_FOUND, "C101", " 콘텐츠를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
