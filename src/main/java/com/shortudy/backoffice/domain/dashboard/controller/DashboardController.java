package com.shortudy.backoffice.domain.dashboard.controller;

import com.shortudy.backoffice.domain.dashboard.dto.response.CategoryShortsCountResponse;
import com.shortudy.backoffice.domain.dashboard.dto.response.DailyUploadCountResponse;
import com.shortudy.backoffice.domain.dashboard.dto.response.PublishConversionRateResponse;
import com.shortudy.backoffice.domain.dashboard.service.DashboardService;
import com.shortudy.backoffice.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 대시보드 API 컨트롤러
 * 담당자: 민수(업로드 수, 전환율)
 */
@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    /**
     * 일 단위 업로드 수 조회
     * 담당자: 민수
     * 정책: PUBLISHED 상태의 published_at 기준 집계
     */
    @GetMapping("/uploads")
    public ApiResponse<List<DailyUploadCountResponse>> getDailyUploadCounts(
            // 조회 일수 (기본 30일)
            @RequestParam(defaultValue = "30") int days
    ) {
        return ApiResponse.success(dashboardService.getUploadStats(days), "일 단위 업로드 수 조회 성공");
    }

    /**
     * 게시(PUBLISHED) 전환율 조회
     * 담당자: 민수
     * 정책: 업로드 수와 동일한 days 기간 기준 계산
     */
    @GetMapping("/conversion-rate")
    public ApiResponse<PublishConversionRateResponse> getPublishedConversionRate(
            // 조회 일수 (기본 30일)
            @RequestParam(defaultValue = "30") int days
    ) {
        return ApiResponse.success(dashboardService.getConversionRate(days), "게시 전환율 조회 성공");
    }

    /**
     * 카테고리별 쇼츠 수 조회
     */
    @GetMapping("/categories/shorts-count")
    public ApiResponse<List<CategoryShortsCountResponse>> getCategoryShortsCount() {
        return ApiResponse.success(dashboardService.getCategoryShortsCount(), "카테고리별 쇼츠 수 조회 성공");
    }
}
