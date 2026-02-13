package com.shortudy.backoffice.domain.content.repository;

import com.shortudy.backoffice.domain.content.entity.Keyword;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    boolean existsByNormalizedName(String normalizedName);

    @Query(value = "SELECT k.id AS id, k.display_name AS name FROM `keyword` k ORDER BY k.id ASC", nativeQuery = true)
    List<KeywordSummaryProjection> findKeywordSummaries();

    interface KeywordSummaryProjection {
        Long getId();

        String getName();
    }
}
