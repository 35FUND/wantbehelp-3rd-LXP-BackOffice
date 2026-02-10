package com.shortudy.backoffice.domain.auth.controller;

import com.shortudy.backoffice.domain.auth.dto.request.LoginRequest;
import com.shortudy.backoffice.domain.auth.dto.response.LoginResponse;
import com.shortudy.backoffice.domain.auth.service.AuthService;
import com.shortudy.backoffice.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 인증 관련 API 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * 관리자 로그인
     */
    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ApiResponse.success(response, "로그인에 성공하였습니다.");
    }
}
