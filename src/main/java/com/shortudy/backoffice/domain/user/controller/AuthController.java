package com.shortudy.backoffice.domain.user.controller;

import com.shortudy.backoffice.domain.user.dto.request.LoginRequest;
import com.shortudy.backoffice.domain.user.dto.request.RefreshRequest;
import com.shortudy.backoffice.domain.user.dto.response.LoginResponse;
import com.shortudy.backoffice.domain.user.service.AuthService;
import com.shortudy.backoffice.global.common.ApiResponse;
import com.shortudy.backoffice.global.security.principal.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @PostMapping("/refresh")
    public ApiResponse<LoginResponse> refresh(@RequestBody RefreshRequest request) {
        LoginResponse response = authService.refresh(request);
        return ApiResponse.success(response);
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(@AuthenticationPrincipal CustomUserDetails details) {
        authService.logout(details.getId());
        return ApiResponse.success(null);
    }
}
