package com.shortudy.backoffice.domain.comment.controller;

import com.shortudy.backoffice.domain.comment.dto.request.DeleteCommentReportRequest;
import com.shortudy.backoffice.domain.comment.dto.response.BackofficeCommentReportSummaryResponse;
import com.shortudy.backoffice.domain.comment.dto.response.BackofficePageResponse;
import com.shortudy.backoffice.domain.comment.entity.ReportStatus;
import com.shortudy.backoffice.domain.comment.service.CommentReportService;
import com.shortudy.backoffice.global.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 백오피스 댓글 신고 관리 API
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/backoffice/comment-reports")
public class BackofficeCommentReportController {

    private final CommentReportService commentReportService;

    /**
     * 신고 목록 조회 (페이징/필터)
     */
    @GetMapping
    public ApiResponse<BackofficePageResponse<BackofficeCommentReportSummaryResponse>> getReports(
            @RequestParam(required = false) ReportStatus status,
            @RequestParam(required = false, defaultValue = "") String keyword,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ApiResponse.success(commentReportService.findBackofficeReports(status, keyword, pageable));
    }

    /**
     * 신고 상세 조회
     */
    @GetMapping("/{reportId}")
    public ApiResponse<BackofficeCommentReportSummaryResponse> getReport(@PathVariable Long reportId) {
        return ApiResponse.success(commentReportService.findBackofficeReport(reportId));
    }

    /**
     * 신고 처리 완료
     */
    @PatchMapping("/{reportId}/process")
    public ApiResponse<Void> processReport(@PathVariable Long reportId) {
        commentReportService.processReport(reportId);
        return ApiResponse.success(null, "신고가 처리 완료되었습니다.");
    }

    /**
     * 신고 댓글 삭제 처리
     */
    @PatchMapping("/{reportId}/delete")
    public ApiResponse<Void> deleteReportedComment(
            @PathVariable Long reportId,
            @Valid @RequestBody DeleteCommentReportRequest request
    ) {
        commentReportService.deleteReportedComment(reportId, request.getReason());
        return ApiResponse.success(null, "댓글이 소프트 삭제 처리되었습니다.");
    }
}
