package com.shortudy.backoffice.domain.comment.controller;

import com.shortudy.backoffice.domain.comment.dto.request.ReportCommentRequest;
import com.shortudy.backoffice.domain.comment.service.CommentReportService;
import com.shortudy.backoffice.global.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 클라이언트 댓글 신고 API
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentReportService commentReportService;

    /**
     * 댓글 신고 생성
     */
    @PostMapping("/{commentId}/reports")
    public ApiResponse<Void> reportComment(
            @PathVariable Long commentId,
            @Valid @RequestBody ReportCommentRequest request
    ) {
        commentReportService.reportComment(request.getReporterId(), commentId, request.getReason());
        return ApiResponse.success(null, "댓글 신고가 접수되었습니다.");
    }
}
