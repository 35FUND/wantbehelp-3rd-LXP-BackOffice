package com.shortudy.backoffice.domain.auth.service;

import com.shortudy.backoffice.domain.auth.entity.Admin;
import com.shortudy.backoffice.domain.auth.repository.AdminRepository;
import com.shortudy.backoffice.domain.auth.dto.request.LoginRequest;
import com.shortudy.backoffice.domain.auth.dto.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 인증 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final AdminRepository adminRepository;

    /**
     * 관리자 로그인
     */
    public LoginResponse login(LoginRequest request) {
        Admin admin = adminRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다."));

        // TODO: 비밀번호 검증 로직 (BCrypt 등 적용 필요)
        if (!admin.getPassword().equals(request.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        // TODO: JWT 토큰 생성 로직 구현 필요
        String mockToken = "eyJhbGciOiJIUzI1NiJ9..."; 

        return LoginResponse.builder()
                .accessToken(mockToken)
                .adminName(admin.getName())
                .build();
    }
}
