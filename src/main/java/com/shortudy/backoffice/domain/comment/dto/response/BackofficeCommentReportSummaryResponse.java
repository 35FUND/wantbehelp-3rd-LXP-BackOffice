package com.shortudy.backoffice.domain.comment.dto.response;

import com.shortudy.backoffice.domain.comment.entity.CommentReport;
import com.shortudy.backoffice.domain.comment.entity.ReportStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 백오피스 댓글 신고 목록/상세 공통 응답 DTO
 */
@Getter
@Builder
public class BackofficeCommentReportSummaryResponse {
    private Long reportId;
    private Long commentId;
    private Long reporterId;
    private String reporterName;
    private String reason;
    private ReportStatus status;
    private LocalDateTime createdAt;

    // 운영 판단용 필드(현재 도메인 데이터가 없어 null 반환)
    private String commentContent;
    private Long commentWriterId;
    private Long shortsId;

    public static BackofficeCommentReportSummaryResponse from(CommentReport report, String reporterName) {
        return BackofficeCommentReportSummaryResponse.builder()
                .reportId(report.getId())
                .commentId(report.getCommentId())
                .reporterId(report.getReporterId())
                .reporterName(reporterName)
                .reason(report.getReason())
                .status(report.getStatus())
                .createdAt(report.getCreatedAt())
                .commentContent(null)
                .commentWriterId(null)
                .shortsId(null)
                .build();
    }
}
