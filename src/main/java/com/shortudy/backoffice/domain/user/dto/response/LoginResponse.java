package com.shortudy.backoffice.domain.user.dto.response;

public record LoginResponse(
        String accessToken,
        String refreshToken
) {
}
