package com.shortudy.backoffice.domain.dashboard.controller;

import com.shortudy.backoffice.domain.dashboard.dto.response.UserStatsResponse;
import com.shortudy.backoffice.domain.dashboard.service.UserStatsService;
import com.shortudy.backoffice.global.common.ApiResponse;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/stats/users")
public class UserStatsController {

    private final UserStatsService userStatsService;

    public UserStatsController(UserStatsService userStatsService) {
        this.userStatsService = userStatsService;
    }

    @GetMapping
    @Validated
    public ApiResponse<UserStatsResponse> getUserStats(
        // 조회 일수 (기본 30일), 1~365일 범위의 검증 로직 설정
        @RequestParam(defaultValue = "30") @Min(1) @Max(365) int days
    ) {
        UserStatsResponse stats = userStatsService.getUserStats(days);
        return ApiResponse.success(stats);
    }
}
