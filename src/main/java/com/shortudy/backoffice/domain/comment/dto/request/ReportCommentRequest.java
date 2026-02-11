package com.shortudy.backoffice.domain.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

/**
 * 댓글 신고 요청 DTO
 */
@Getter
public class ReportCommentRequest {

    @NotNull
    private Long reporterId;

    @NotBlank
    @Size(max = 500)
    private String reason;
}
