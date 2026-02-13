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
    CONTENT_NOT_FOUND(HttpStatus.NOT_FOUND, "C101", " 콘텐츠를 찾을 수 없습니다."),
    CONTENT_REJECT_REASON_REQUIRED(HttpStatus.BAD_REQUEST, "C102", " 반려 사유를 선택해주세요."),

    // Comment Report
    COMMENT_REPORT_NOT_FOUND(HttpStatus.NOT_FOUND, "CR001", " 신고 내역을 찾을 수 없습니다."),
    COMMENT_ALREADY_REPORTED(HttpStatus.CONFLICT, "CR002", " 이미 신고한 댓글입니다."),
    COMMENT_REPORT_FORBIDDEN(HttpStatus.FORBIDDEN, "CR003", " 본인 댓글은 신고할 수 없습니다."),
    COMMENT_REPORT_ALREADY_HANDLED(HttpStatus.CONFLICT, "CR004", " 이미 처리된 신고입니다."),
    COMMENT_DELETE_REASON_REQUIRED(HttpStatus.BAD_REQUEST, "CR005", " 삭제 사유를 선택해주세요."),
    COMMENT_SOFT_DELETE_UNSUPPORTED(HttpStatus.INTERNAL_SERVER_ERROR, "CR006", " 댓글 소프트 삭제 스키마를 확인해주세요."),
    COMMENT_SOFT_DELETE_TARGET_NOT_FOUND(HttpStatus.NOT_FOUND, "CR007", " 삭제 대상 댓글을 찾을 수 없습니다.");

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
