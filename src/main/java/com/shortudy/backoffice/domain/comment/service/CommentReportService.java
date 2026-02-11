package com.shortudy.backoffice.domain.comment.service;

import com.shortudy.backoffice.domain.comment.dto.response.BackofficeCommentReportSummaryResponse;
import com.shortudy.backoffice.domain.comment.dto.response.BackofficePageResponse;
import com.shortudy.backoffice.domain.comment.entity.CommentReport;
import com.shortudy.backoffice.domain.comment.entity.ReportStatus;
import com.shortudy.backoffice.domain.comment.repository.CommentReportRepository;
import com.shortudy.backoffice.global.error.BaseException;
import com.shortudy.backoffice.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 댓글 신고 서비스
 * 비즈니스 로직을 처리합니다.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentReportService {

    private final CommentReportRepository commentReportRepository;

    /**
     * 모든 신고 목록을 조회합니다.
     */
    public List<CommentReport> findAllReports() {
        return commentReportRepository.findAll();
    }

    /**
     * 백오피스 신고 목록 조회(페이징/필터)
     */
    public BackofficePageResponse<BackofficeCommentReportSummaryResponse> findBackofficeReports(
            ReportStatus status,
            String keyword,
            Pageable pageable
    ) {
        Page<CommentReport> reports = commentReportRepository.search(status, keyword, pageable);

        return BackofficePageResponse.<BackofficeCommentReportSummaryResponse>builder()
                .content(reports.getContent().stream().map(BackofficeCommentReportSummaryResponse::from).toList())
                .page(reports.getNumber())
                .size(reports.getSize())
                .totalElements(reports.getTotalElements())
                .totalPages(reports.getTotalPages())
                .first(reports.isFirst())
                .last(reports.isLast())
                .build();
    }

    /**
     * 백오피스 신고 단건 상세 조회
     */
    public BackofficeCommentReportSummaryResponse findBackofficeReport(Long reportId) {
        return BackofficeCommentReportSummaryResponse.from(getReportOrThrow(reportId));
    }

    /**
     * 클라이언트 신고 생성
     */
    @Transactional
    public void reportComment(Long reporterId, Long commentId, String reason) {
        if (commentReportRepository.existsByCommentIdAndReporterId(commentId, reporterId)) {
            throw new BaseException(ErrorCode.COMMENT_ALREADY_REPORTED);
        }

        // 현재 도메인에는 댓글 작성자 정보가 없어, 자기 댓글 신고 금지 검증은 추후 댓글 도메인 연동 시 보강한다.
        CommentReport report = CommentReport.builder()
                .commentId(commentId)
                .reporterId(reporterId)
                .reason(reason)
                .build();
        commentReportRepository.save(report);
    }

    /**
     * 신고를 처리 상태로 변경합니다.
     */
    @Transactional
    public void processReport(Long reportId) {
        CommentReport report = getReportOrThrow(reportId);
        report.process();
    }

    /**
     * 신고를 반려 상태로 변경합니다.
     */
    @Transactional
    public void rejectReport(Long reportId) {
        CommentReport report = getReportOrThrow(reportId);
        report.reject();
    }

    private CommentReport getReportOrThrow(Long reportId) {
        return commentReportRepository.findById(reportId)
                .orElseThrow(() -> new BaseException(ErrorCode.COMMENT_REPORT_NOT_FOUND));
    }
}
