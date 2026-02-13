package com.shortudy.backoffice.domain.content.controller;

import com.shortudy.backoffice.domain.content.dto.request.UpdateShortsStatusRequest;
import com.shortudy.backoffice.domain.content.dto.response.InspectionTriggerResponse;
import com.shortudy.backoffice.domain.content.dto.response.ShortsInspectionResultResponse;
import com.shortudy.backoffice.domain.content.dto.response.ShortsReviewPageResponse;
import com.shortudy.backoffice.domain.content.entity.ShortsStatus;
import com.shortudy.backoffice.domain.content.service.ShortsReviewService;
import com.shortudy.backoffice.global.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 백오피스 영상 검수 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/backoffice/shorts/reviews")
public class BackofficeShortsReviewController {

    private final ShortsReviewService shortsReviewService;

    @GetMapping
    public ApiResponse<ShortsReviewPageResponse> getReviewItems(
            @RequestParam(required = false) ShortsStatus status,
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ApiResponse.success(shortsReviewService.getReviewItems(status, pageable));
    }

    @GetMapping("/{shortsId}/inspection-result")
    public ApiResponse<ShortsInspectionResultResponse> getLatestInspectionResult(@PathVariable Long shortsId) {
        return ApiResponse.success(shortsReviewService.getLatestInspectionResult(shortsId));
    }

    @PostMapping("/{shortsId}/inspect")
    public ApiResponse<InspectionTriggerResponse> triggerInspection(@PathVariable Long shortsId) {
        return ApiResponse.success(shortsReviewService.triggerInspection(shortsId), "AI 검수를 시작했습니다.");
    }

    @PatchMapping("/{shortsId}/status")
    public ApiResponse<Void> updateStatus(
            @PathVariable Long shortsId,
            @Valid @RequestBody UpdateShortsStatusRequest request
    ) {
        shortsReviewService.updateStatus(shortsId, request.getStatus(), request.getRejectReason());
        return ApiResponse.success(null, "영상 상태가 변경되었습니다.");
    }
}
