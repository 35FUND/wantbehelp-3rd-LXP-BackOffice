package com.shortudy.backoffice.domain.dashboard.service;

import com.shortudy.backoffice.domain.content.entity.ShortsStatus;
import com.shortudy.backoffice.domain.content.repository.CategoryRepository;
import com.shortudy.backoffice.domain.content.repository.ShortsRepository;
import com.shortudy.backoffice.domain.dashboard.dto.response.CategoryShortsCountResponse;
import com.shortudy.backoffice.domain.dashboard.dto.response.DailyUploadCountResponse;
import com.shortudy.backoffice.domain.dashboard.dto.response.PublishConversionRateResponse;
import com.shortudy.backoffice.global.error.BaseException;
import com.shortudy.backoffice.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 대시보드 통계 서비스
 * 담당: 민수(업로드/전환율)
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardService {

    private static final int DEFAULT_DAILY_RANGE_DAYS = 30;
    private static final int MAX_DAILY_RANGE_DAYS = 365;
    private static final ZoneId KOREA_ZONE = ZoneId.of("Asia/Seoul");

    private final ShortsRepository shortsRepository;
    private final CategoryRepository categoryRepository;

    /**
     * 일 단위 업로드 수 조회 (민수)
     * 정책: PUBLISHED 상태만 published_at 기준으로 집계한다.
     */
    public List<DailyUploadCountResponse> getUploadStats(int days) {
        int safeDays = normalizeDays(days);
        DateRange range = resolveKoreaDateRange(safeDays);

        return shortsRepository.findDailyUploadCount(ShortsStatus.PUBLISHED, range.from(), range.to());
    }

    /**
     * 게시 전환율 조회 (민수)
     */
    public PublishConversionRateResponse getConversionRate(int days) {
        int safeDays = normalizeDays(days);
        DateRange range = resolveKoreaDateRange(safeDays);

        long totalCount = shortsRepository.count();
        long publishedCount = shortsRepository.countByStatus(ShortsStatus.PUBLISHED);

        double conversionRate = 0.0;
        if (totalCount > 0) {
            conversionRate = BigDecimal.valueOf((double) publishedCount * 100 / totalCount)
                    .setScale(2, RoundingMode.HALF_UP)
                    .doubleValue();
        }

        return PublishConversionRateResponse.builder()
                .totalCount(totalCount)
                .publishedCount(publishedCount)
                .conversionRate(conversionRate)
                .build();
    }

    /**
     * 카테고리별 쇼츠 수 조회
     */
    public List<CategoryShortsCountResponse> getCategoryShortsCount() {
        return categoryRepository.findCategoryShortsCounts(ShortsStatus.PUBLISHED)
                .stream()
                .map(CategoryShortsCountResponse::from)
                .collect(Collectors.toList());
    }

    private int normalizeDays(int days) {
        // days 미입력/0 입력 시 기본 조회 기간(30일)을 적용한다.
        if (days == 0) {
            return DEFAULT_DAILY_RANGE_DAYS;
        }
        // 과도한 조회 범위를 제한해 DB 부하를 방지한다.
        if (days < 0 || days > MAX_DAILY_RANGE_DAYS) {
            throw new BaseException(ErrorCode.INVALID_INPUT_VALUE);
        }
        return days;
    }

    /**
     * KST(Asia/Seoul) 기준 [from, to) 기간을 계산한다.
     */
    private DateRange resolveKoreaDateRange(int days) {
        LocalDateTime to = LocalDate.now(KOREA_ZONE).plusDays(1L).atStartOfDay();
        LocalDateTime from = to.minusDays(days);
        return new DateRange(from, to);
    }

    private record DateRange(LocalDateTime from, LocalDateTime to) {
    }

}
