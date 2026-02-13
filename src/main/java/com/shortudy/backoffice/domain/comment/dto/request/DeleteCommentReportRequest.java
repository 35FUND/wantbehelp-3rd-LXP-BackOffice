package com.shortudy.backoffice.domain.comment.dto.request;

import com.shortudy.backoffice.domain.comment.entity.CommentDeleteReason;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

/**
 * 댓글 신고 삭제 처리 요청 DTO
 */
@Getter
public class DeleteCommentReportRequest {

    @NotNull
    private CommentDeleteReason reason;
}
