package com.shortudy.backoffice.domain.content.repository;

import com.shortudy.backoffice.domain.content.entity.Shorts;
import com.shortudy.backoffice.domain.content.entity.ShortsStatus;
import com.shortudy.backoffice.domain.dashboard.dto.response.DailyUploadCountResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 숏츠 조회 저장소
 */
public interface ShortsRepository extends JpaRepository<Shorts, Long> {

    /**
     * 임시 자동 검수 폴링 대상(PENDING + videoUrl 존재) 목록을 생성일 오름차순으로 조회한다.
     */
    List<Shorts> findByStatusAndVideoUrlIsNotNullOrderByCreatedAtAsc(ShortsStatus status, Pageable pageable);

    @Query(
            value = """
                    select s
                    from Shorts s
                    where (:status is null or s.status = :status)
                    """,
            countQuery = """
                    select count(s)
                    from Shorts s
                    where (:status is null or s.status = :status)
                    """
    )
    Page<Shorts> findByStatusFilter(@Param("status") ShortsStatus status, Pageable pageable);

    /**
     * 특정 상태의 숏츠 개수를 조회한다.
     */
    long countByStatus(ShortsStatus status);

    /**
     * 기간 내 생성된 전체 숏츠 개수를 조회한다.
     */
    long countByCreatedAtGreaterThanEqualAndCreatedAtLessThan(LocalDateTime from, LocalDateTime to);

    /**
     * 기간 내 생성된 특정 상태 숏츠 개수를 조회한다.
     */
    long countByStatusAndCreatedAtGreaterThanEqualAndCreatedAtLessThan(
            ShortsStatus status,
            LocalDateTime from,
            LocalDateTime to
    );

    /**
     * 게시 완료(PUBLISHED)된 숏츠를 published_at 기준으로 일 단위 집계한다.
     * - from 이상, to 미만의 반열린 구간을 사용한다.
     * - JPQL function('date')를 사용해 시간 정보를 제거한다.
     */
    @Query("""
            select new com.shortudy.backoffice.domain.dashboard.dto.response.DailyUploadCountResponse(
                function('date', s.publishedAt),
                count(s)
            )
            from Shorts s
            where s.status = :status
              and s.publishedAt is not null
              and s.publishedAt >= :from and s.publishedAt < :to
            group by function('date', s.publishedAt)
            order by function('date', s.publishedAt)
            """)
    List<DailyUploadCountResponse> findDailyUploadCount(@Param("status") ShortsStatus status,
                                                        @Param("from") LocalDateTime from,
                                                        @Param("to") LocalDateTime to);
}
