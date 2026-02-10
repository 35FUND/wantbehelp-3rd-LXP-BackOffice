package com.shortudy.backoffice.domain.comment.controller;

import com.shortudy.backoffice.domain.comment.dto.response.CommentReportResponse;
import com.shortudy.backoffice.domain.comment.service.CommentReportService;
import com.shortudy.backoffice.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 댓글 신고 관련 API 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments/reports")
public class CommentReportController {

    private final CommentReportService commentReportService;

    /**
     * 댓글 신고 목록 조회
     */
    @GetMapping
    public ApiResponse<List<CommentReportResponse>> getReports() {
        List<CommentReportResponse> response = commentReportService.findAllReports().stream()
                .map(CommentReportResponse::from)
                .collect(Collectors.toList());
        return ApiResponse.success(response);
    }

    /**
     * 댓글 신고 처리
     */
    @PatchMapping("/{reportId}/process")
    public ApiResponse<Void> processReport(@PathVariable Long reportId) {
        commentReportService.processReport(reportId);
        return ApiResponse.success(null, "신고가 성공적으로 처리되었습니다.");
    }
}
