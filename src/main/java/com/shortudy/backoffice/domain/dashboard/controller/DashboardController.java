package com.shortudy.backoffice.domain.dashboard.controller;

import com.shortudy.backoffice.domain.dashboard.service.DashboardService;
import com.shortudy.backoffice.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 대시보드 API 컨트롤러
 * 담당자: 세훈(가입 추이), 민수(업로드 수, 전환율)
 */
@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    /**
     * 가입자 수 및 신규 가입 추이 조회
     * 담당자: 세훈
     */
    @GetMapping("/subscriptions")
    public ApiResponse<Object> getSubscriptionTrends() {
        return ApiResponse.success(dashboardService.getSubscriberStats(), "가입 추이 데이터 조회 성공");
    }

    /**
     * 일 단위 업로드 수 조회
     * 담당자: 민수
     */
    @GetMapping("/uploads")
    public ApiResponse<Object> getDailyUploadCounts() {
        return ApiResponse.success(dashboardService.getUploadStats(), "일 단위 업로드 수 조회 성공");
    }

    /**
     * 게시(PUBLISHED) 전환율 조회
     * 담당자: 민수
     */
    @GetMapping("/conversion-rate")
    public ApiResponse<Object> getPublishedConversionRate() {
        return ApiResponse.success(dashboardService.getConversionRate(), "게시 전환율 조회 성공");
    }
}
