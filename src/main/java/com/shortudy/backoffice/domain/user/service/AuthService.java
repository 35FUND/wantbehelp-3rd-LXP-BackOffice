package com.shortudy.backoffice.domain.user.service;

import com.shortudy.backoffice.domain.user.dto.request.LoginRequest;
import com.shortudy.backoffice.domain.user.dto.request.RefreshRequest;
import com.shortudy.backoffice.domain.user.dto.response.LoginResponse;
import com.shortudy.backoffice.domain.user.entity.RefreshToken;
import com.shortudy.backoffice.domain.user.entity.User;
import com.shortudy.backoffice.domain.user.repository.RefreshTokenRepository;
import com.shortudy.backoffice.domain.user.repository.UserRepository;
import com.shortudy.backoffice.global.error.BaseException;
import com.shortudy.backoffice.global.error.ErrorCode;
import com.shortudy.backoffice.global.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 인증 서비스
 */
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            UserRepository userRepository,
            RefreshTokenRepository refreshTokenRepository,
            JwtTokenProvider jwtTokenProvider,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 관리자 로그인
     */
    @Transactional
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다."));

        // bcrypt 해시 저장을 기본으로 검증하고, 레거시 평문 데이터는 임시 호환한다.
        boolean matches = passwordEncoder.matches(request.password(), user.getPassword())
                || user.getPassword().equals(request.password());
        if (!matches) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        // TODO: JWT 토큰 생성 로직 구현 필요
        String mockToken = "eyJhbGciOiJIUzI1NiJ9..."; 

        return generateToken(user);
    }

    @Transactional
    public LoginResponse refresh(RefreshRequest request) {
        // 토큰 검증 로직
        jwtTokenProvider.validateToken(request.refreshToken());

        // 토큰에서 userId 추출
        Long userId = jwtTokenProvider.getUserIdFromToken(request.refreshToken());
        User user = userRepository.findById(userId).orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));
        // 유효한 세션이 아니거나 이미 로그아웃된 상태 -> 다시 로그인을 유도
        RefreshToken storedToken = refreshTokenRepository.findByUserId(userId).orElseThrow(() -> new BaseException(ErrorCode.LOGIN_REQUIRED));

        // RTR 검증 (토큰 탈취 의심 상황 -> DB에서 토큰 삭제 후 로그아웃 유도용 Exception)
        if (!storedToken.getToken().equals(request.refreshToken())) {
            refreshTokenRepository.delete(storedToken);
            throw new BaseException(ErrorCode.INVALID_TOKEN);
        }

        return generateToken(user);
    }

    private LoginResponse generateToken(User user) {

        // 액세스 토큰 생성
        String accessToken = jwtTokenProvider.createAccessToken(
                user.getId(),
                user.getEmail(),
                user.getRole()
        );

        // 리프레시 토큰 생성
        String refreshToken = jwtTokenProvider.createRefreshToken(
                user.getId(),
                user.getEmail(),
                user.getRole()
        );

        // 리프레쉬 토큰이 없으면 생성
        RefreshToken refreshTokenEntity = refreshTokenRepository.findByUserId(user.getId())
                .orElse(RefreshToken.create(user.getId(), refreshToken));

        // 리프레쉬 토큰이 존재하면 업데이트
        refreshTokenEntity.updateToken(refreshToken);
        refreshTokenRepository.save(refreshTokenEntity);

        return new LoginResponse(accessToken, refreshTokenEntity.getToken());
    }

    @Transactional
    public void logout(Long userId) {
        RefreshToken storedToken = refreshTokenRepository.findByUserId(userId).orElseThrow(() -> new BaseException(ErrorCode.LOGIN_REQUIRED));
        refreshTokenRepository.delete(storedToken);
    }
}

