package com.shortudy.backoffice.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 전역 에러 코드 정의
 */
@Getter
public enum ErrorCode {
    // Common
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001", " 올바르지 않은 입력값입니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C002", " 지원하지 않는 HTTP 메서드입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C003", " 서버 내부 오류가 발생했습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON_401", "요청 권한이 없습니다."),


    // authenticated
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "USER_401", "유효하지 않은 토큰정보입니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "USER_401", "토큰이 만료되었습니다."),

    // user
    LOGIN_REQUIRED(HttpStatus.UNAUTHORIZED, "USER_401", "로그인이 필요합니다."),


    // Domain Specific
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "M001", " 사용자를 찾을 수 없습니다."),
    CONTENT_NOT_FOUND(HttpStatus.NOT_FOUND, "C101", " 콘텐츠를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public HttpStatus status() {
        return status;
    }

    public String code() {
        return code;
    }

    public String message() {
        return message;
    }
}
