package com.shortudy.backoffice.domain.user.dto.request;

import com.shortudy.backoffice.domain.user.entity.UserStatus;

public record UpdateRequest(
        UserStatus userStatus
) {
}
