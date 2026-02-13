package com.shortudy.backoffice.domain.comment.entity;

import com.shortudy.backoffice.global.error.BaseException;
import com.shortudy.backoffice.global.error.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 댓글 신고 엔티티
 */
@Entity
@Getter
@Table(name = "comment_reports")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long commentId; // 신고 대상 댓글 ID

    @Column(nullable = false)
    private Long reporterId; // 신고자 ID

    @Column(nullable = false)
    private String reason; // 신고 사유

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportStatus status; // 신고 처리 상태

    @Column
    private String actionReason; // 운영자 처리 사유

    @Builder
    public CommentReport(Long commentId, Long reporterId, String reason) {
        this.commentId = commentId;
        this.reporterId = reporterId;
        this.reason = reason;
        this.status = ReportStatus.PENDING;
    }

    public void process() {
        validateNotHandled();
        this.status = ReportStatus.PROCESSED;
        this.actionReason = null;
    }

    public void reject(CommentDeleteReason deleteReason) {
        validateNotHandled();
        this.status = ReportStatus.REJECTED;
        this.actionReason = deleteReason.getDescription();
    }

    private void validateNotHandled() {
        if (this.status == ReportStatus.PROCESSED || this.status == ReportStatus.REJECTED) {
            throw new BaseException(ErrorCode.COMMENT_REPORT_ALREADY_HANDLED);
        }
    }
}
