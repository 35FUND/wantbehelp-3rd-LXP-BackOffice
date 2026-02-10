package com.shortudy.backoffice.domain.auth.dto.response;

import lombok.Builder;
import lombok.Getter;

/**
 * 로그인 응답 DTO
 */
@Getter
@Builder
public class LoginResponse {
    private String accessToken;
    private String adminName;
}
