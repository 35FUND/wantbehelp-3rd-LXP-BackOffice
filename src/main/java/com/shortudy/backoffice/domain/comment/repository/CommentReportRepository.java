package com.shortudy.backoffice.domain.comment.repository;

import com.shortudy.backoffice.domain.comment.entity.CommentReport;
import com.shortudy.backoffice.domain.comment.entity.ReportStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 댓글 신고 저장소 인터페이스
 */
public interface CommentReportRepository extends JpaRepository<CommentReport, Long> {
    boolean existsByCommentIdAndReporterId(Long commentId, Long reporterId);

    @Query("""
            select cr.commentId
            from CommentReport cr
            where cr.reporterId = :reporterId
              and cr.commentId in :commentIds
            """)
    List<Long> findReportedCommentIds(@Param("reporterId") Long reporterId, @Param("commentIds") List<Long> commentIds);

    @Query(
            value = """
                    select cr
                    from CommentReport cr
                     where (:status is null or cr.status = :status)
                       and (:keyword is null or :keyword = ''
                            or lower(cr.reason) like lower(concat('%', :keyword, '%'))
                            or lower(coalesce(cr.actionReason, '')) like lower(concat('%', :keyword, '%'))
                            or str(cr.commentId) like concat('%', :keyword, '%'))
                     """,
            countQuery = """
                     select count(cr)
                     from CommentReport cr
                     where (:status is null or cr.status = :status)
                       and (:keyword is null or :keyword = ''
                            or lower(cr.reason) like lower(concat('%', :keyword, '%'))
                            or lower(coalesce(cr.actionReason, '')) like lower(concat('%', :keyword, '%'))
                            or str(cr.commentId) like concat('%', :keyword, '%'))
                     """
    )
    Page<CommentReport> search(@Param("status") ReportStatus status, @Param("keyword") String keyword, Pageable pageable);
}
