package com.shortudy.backoffice.global.common;

import lombok.Getter;
import java.time.LocalDateTime;

/**
 * 모든 API의 공통 응답 포맷
 */
@Getter
public class ApiResponse<T> {
    private final boolean success;
    private final T data;
    private final String message;
    private final LocalDateTime timestamp;

    private ApiResponse(boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, "요청이 성공적으로 처리되었습니다.");
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(true, data, message);
    }

    public static ApiResponse<Void> error(String message) {
        return new ApiResponse<>(false, null, message);
    }
}
