package com.shortudy.backoffice.domain.user.dto.response;

import com.shortudy.backoffice.domain.user.entity.UserRole;
import com.shortudy.backoffice.domain.user.entity.UserStatus;

import java.time.LocalDateTime;

public interface UserProjection {

    String getEmail();
    String getNickname();
    UserRole getRole();
    UserStatus getStatus();
    LocalDateTime getCreatedAt();
    LocalDateTime getLastLoginAt();
}
