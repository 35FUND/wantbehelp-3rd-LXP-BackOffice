package com.shortudy.backoffice.domain.comment.repository;

import com.shortudy.backoffice.domain.comment.entity.CommentReport;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 댓글 신고 저장소 인터페이스
 */
public interface CommentReportRepository extends JpaRepository<CommentReport, Long> {
}
