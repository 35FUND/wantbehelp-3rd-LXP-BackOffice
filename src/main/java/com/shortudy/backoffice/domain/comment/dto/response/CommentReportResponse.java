package com.shortudy.backoffice.domain.comment.dto.response;

import com.shortudy.backoffice.domain.comment.entity.CommentReport;
import com.shortudy.backoffice.domain.comment.entity.ReportStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 댓글 신고 조회 응답 DTO
 */
@Getter
@Builder
public class CommentReportResponse {
    private Long id;
    private Long commentId;
    private Long reporterId;
    private String reason;
    private ReportStatus status;
    private LocalDateTime createdAt;

    public static CommentReportResponse from(CommentReport report) {
        return CommentReportResponse.builder()
                .id(report.getId())
                .commentId(report.getCommentId())
                .reporterId(report.getReporterId())
                .reason(report.getReason())
                .status(report.getStatus())
                .createdAt(report.getCreatedAt())
                .build();
    }
}
