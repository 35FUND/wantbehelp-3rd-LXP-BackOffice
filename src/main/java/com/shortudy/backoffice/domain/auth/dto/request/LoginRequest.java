package com.shortudy.backoffice.domain.auth.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 로그인 요청 DTO
 */
@Getter
@NoArgsConstructor
public class LoginRequest {
    private String email;
    private String password;
}
