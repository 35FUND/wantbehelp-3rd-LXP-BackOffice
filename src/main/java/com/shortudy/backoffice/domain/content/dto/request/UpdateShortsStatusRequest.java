package com.shortudy.backoffice.domain.content.dto.request;

import com.shortudy.backoffice.domain.content.entity.ShortsStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

/**
 * 쇼츠 상태 변경 요청 DTO
 */
@Getter
public class UpdateShortsStatusRequest {

    @NotNull
    private ShortsStatus status;
}
