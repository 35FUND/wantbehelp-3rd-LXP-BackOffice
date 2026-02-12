package com.shortudy.backoffice.domain.content.repository;

import com.shortudy.backoffice.domain.content.entity.ShortsInspectionResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * AI 검수 결과 저장소
 */
public interface ShortsInspectionResultRepository extends JpaRepository<ShortsInspectionResult, Long> {
    Optional<ShortsInspectionResult> findTopByShortsIdOrderByCreatedAtDesc(Long shortsId);

    List<ShortsInspectionResult> findByShortsIdIn(List<Long> shortsIds);
}
