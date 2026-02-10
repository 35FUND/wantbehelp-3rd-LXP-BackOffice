package com.shortudy.backoffice.domain.content.controller;

import com.shortudy.backoffice.domain.content.service.ContentService;
import com.shortudy.backoffice.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 콘텐츠 관리 API 컨트롤러
 * 담당자: 승훈
 */
@RestController
@RequestMapping("/api/v1/contents")
@RequiredArgsConstructor
public class ContentController {

    private final ContentService contentService;

    /**
     * 카테고리별 콘텐츠 분포 조회
     */
    @GetMapping("/distribution")
    public ApiResponse<Object> getContentDistributionByCategory() {
        return ApiResponse.success(contentService.getContentDistribution(), "카테고리별 콘텐츠 분포 조회 성공");
    }

    /**
     * 카테고리 추가
     */
    @PostMapping("/categories")
    public ApiResponse<Void> addCategory(@RequestBody String name) {
        contentService.addCategory(name);
        return ApiResponse.success(null, "카테고리가 추가되었습니다.");
    }

    /**
     * 키워드 추가
     */
    @PostMapping("/keywords")
    public ApiResponse<Void> addKeyword(@RequestBody String name) {
        contentService.addKeyword(name);
        return ApiResponse.success(null, "키워드가 추가되었습니다.");
    }
}
