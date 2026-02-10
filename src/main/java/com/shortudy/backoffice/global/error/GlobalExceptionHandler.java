package com.shortudy.backoffice.global.error;

import com.shortudy.backoffice.global.common.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * 전역 예외 처리기
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse<?>> handleBaseException(BaseException e, HttpServletRequest request) {
        return error(e.errorCode(), e.getMessage(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e,
            HttpServletRequest request
    ) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .orElse(ErrorCode.INVALID_INPUT_VALUE.message());

        return error(ErrorCode.INVALID_INPUT_VALUE, message, request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException e,
            HttpServletRequest request
    ) {
        return error(ErrorCode.INVALID_INPUT_VALUE, "요청 파라미터 타입이 올바르지 않습니다.", request);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodNotAllowed(
            HttpRequestMethodNotSupportedException e,
            HttpServletRequest request
    ) {
        return error(ErrorCode.METHOD_NOT_ALLOWED, ErrorCode.METHOD_NOT_ALLOWED.message(), request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception e, HttpServletRequest request) {
        log.error("예기치 못한 오류. path={}", request.getRequestURI(), e);
        return error(ErrorCode.INTERNAL_SERVER_ERROR, ErrorCode.INTERNAL_SERVER_ERROR.message(), request);
    }

    /**
     * HandlerMethodValidationException 처리기
     * - 메서드 레벨 검증(@Validated on method)에서 발생하는 예외 처리
     */
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ApiResponse<Object>> handle(HandlerMethodValidationException ex, Locale locale, HttpServletRequest request) {

        // MethodValidationResult 를 구현하고 있어서 전체 에러를 뽑을 수 있음
        /*
        * 리스트로 받아오는 이유
        * 1. 여러 필드에서 동시에 validation 에러가 발생할 수 있기 때문에
        * - days=0&size=0&sort=xxx 와 같이 여러 필드에 대해서 @NotBlank, @Min, @Pattern이 동시에 깨지면 오류 메시지가 여러개가 됨
        * */
        List<String> messages = ex.getAllErrors().stream()
            .map(err -> messageSource.getMessage(err, locale))
            .collect(Collectors.toList());

        // 400 또는 500 (Spring이 정한 statusCode)
        // 400 : 요청 입력값(파라미터) 검증 실패면 400
        // 500 : 리턴값 검증 실패면 500
        var status = ex.getStatusCode();

        return ResponseEntity.status(status)
            .body(ApiResponse.error("VALIDATION_ERROR", messages.toString(), request.getRequestURI()));
    }

    /**
     * 공통 에러 응답 생성
     */
    private ResponseEntity<ApiResponse<?>> error(ErrorCode errorCode, String message, HttpServletRequest request) {
        return ResponseEntity.status(errorCode.status())
                .body(ApiResponse.error(errorCode.code(), message, request.getRequestURI()));
    }
}
