package com.shortudy.backoffice.domain.comment.repository;

import com.shortudy.backoffice.domain.comment.entity.CommentDeleteReason;
import com.shortudy.backoffice.global.error.BaseException;
import com.shortudy.backoffice.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * 댓글 소프트 삭제를 위한 JDBC 저장소
 */
@Repository
@RequiredArgsConstructor
public class CommentSoftDeleteRepository {

    private static final List<String> COMMENT_TABLE_CANDIDATES = List.of("comments", "comment");
    private static final Set<String> SOFT_DELETE_COLUMNS = Set.of("deleted_at", "is_deleted", "status");

    private final JdbcTemplate jdbcTemplate;

    public void softDeleteByCommentId(Long commentId, CommentDeleteReason deleteReason) {
        String commentTable = resolveCommentTable();
        Set<String> columns = getTableColumns(commentTable);
        if (columns.isEmpty()) {
            throw new BaseException(ErrorCode.COMMENT_SOFT_DELETE_UNSUPPORTED);
        }

        String idColumn = resolveIdColumn(columns);
        if (idColumn == null) {
            throw new BaseException(ErrorCode.COMMENT_SOFT_DELETE_UNSUPPORTED);
        }

        List<String> setClauses = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        if (columns.contains("deleted_at")) {
            setClauses.add("deleted_at = ?");
            params.add(LocalDateTime.now());
        }
        if (columns.contains("is_deleted")) {
            setClauses.add("is_deleted = ?");
            params.add(true);
        }
        if (columns.contains("status")) {
            setClauses.add("status = ?");
            params.add("DELETED");
        }
        if (columns.contains("delete_reason")) {
            setClauses.add("delete_reason = ?");
            params.add(deleteReason.getDescription());
        }
        if (columns.contains("updated_at")) {
            setClauses.add("updated_at = ?");
            params.add(LocalDateTime.now());
        }

        if (setClauses.stream().noneMatch(clause -> SOFT_DELETE_COLUMNS.stream().anyMatch(clause::startsWith))) {
            throw new BaseException(ErrorCode.COMMENT_SOFT_DELETE_UNSUPPORTED);
        }

        String sql = "update " + commentTable + " set " + String.join(", ", setClauses) + " where " + idColumn + " = ?";
        params.add(commentId);
        int updated = jdbcTemplate.update(sql, params.toArray());
        if (updated == 0) {
            throw new BaseException(ErrorCode.COMMENT_SOFT_DELETE_TARGET_NOT_FOUND);
        }
    }

    private String resolveCommentTable() {
        for (String tableName : COMMENT_TABLE_CANDIDATES) {
            if (!getTableColumns(tableName).isEmpty()) {
                return tableName;
            }
        }
        throw new BaseException(ErrorCode.COMMENT_SOFT_DELETE_UNSUPPORTED);
    }

    private Set<String> getTableColumns(String tableName) {
        List<String> columns = jdbcTemplate.query(
                """
                        select lower(column_name)
                        from information_schema.columns
                        where table_schema = database()
                          and table_name = ?
                        """,
                (rs, rowNum) -> rs.getString(1),
                tableName.toLowerCase(Locale.ROOT)
        );
        return new HashSet<>(columns);
    }

    private String resolveIdColumn(Set<String> columns) {
        if (columns.contains("id")) {
            return "id";
        }
        if (columns.contains("comment_id")) {
            return "comment_id";
        }
        return null;
    }
}
