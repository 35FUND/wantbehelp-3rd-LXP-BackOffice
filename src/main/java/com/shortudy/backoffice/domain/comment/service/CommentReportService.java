package com.shortudy.backoffice.domain.comment.service;

import com.shortudy.backoffice.domain.comment.entity.CommentReport;
import com.shortudy.backoffice.domain.comment.repository.CommentReportRepository;
import lombok.RequiredArgsConstructor;
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
     * 신고를 처리 상태로 변경합니다.
     */
    @Transactional
    public void processReport(Long reportId) {
        CommentReport report = commentReportRepository.findById(reportId)
                .orElseThrow(() -> new IllegalArgumentException("해당 신고 내역이 존재하지 않습니다. ID: " + reportId));
        report.process();
    }
}
