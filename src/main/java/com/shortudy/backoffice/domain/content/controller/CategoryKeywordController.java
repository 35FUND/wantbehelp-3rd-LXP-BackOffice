package com.shortudy.backoffice.domain.content.controller;

import com.shortudy.backoffice.domain.content.dto.request.CategoryCreateRequest;
import com.shortudy.backoffice.domain.content.dto.request.KeywordCreateRequest;
import com.shortudy.backoffice.domain.content.dto.response.CategoryListResponse;
import com.shortudy.backoffice.domain.content.dto.response.KeywordListResponse;
import com.shortudy.backoffice.domain.content.service.ContentService;
import com.shortudy.backoffice.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * 카테고리/키워드 관리 API 컨트롤러
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CategoryKeywordController {

    private final ContentService contentService;

    /**
     * 카테고리 목록 조회
     */
    @GetMapping("/categories")
    public ApiResponse<List<CategoryListResponse>> getCategories() {
        return ApiResponse.success(contentService.getCategories(), "카테고리 목록 조회 성공");
    }

    /**
     * 카테고리 추가
     */
    @PostMapping("/categories")
    public ApiResponse<Void> addCategory(@Valid @RequestBody CategoryCreateRequest request) {
        contentService.addCategory(request.getName());
        return ApiResponse.success(null, "카테고리가 추가되었습니다.");
    }

    /**
     * 카테고리 삭제
     */
    @DeleteMapping("/categories/{categoryId}")
    public ApiResponse<Void> deleteCategory(@PathVariable Long categoryId) {
        contentService.deleteCategory(categoryId);
        return ApiResponse.success(null, "카테고리가 삭제되었습니다.");
    }

    /**
     * 키워드 목록 조회
     */
    @GetMapping("/keywords")
    public ApiResponse<List<KeywordListResponse>> getKeywords() {
        return ApiResponse.success(contentService.getKeywords(), "키워드 목록 조회 성공");
    }

    /**
     * 키워드 추가
     */
    @PostMapping("/keywords")
    public ApiResponse<Void> addKeyword(@Valid @RequestBody KeywordCreateRequest request) {
        contentService.addKeyword(request.getDisplayName());
        return ApiResponse.success(null, "키워드가 추가되었습니다.");
    }

    /**
     * 키워드 삭제
     */
    @DeleteMapping("/keywords/{keywordId}")
    public ApiResponse<Void> deleteKeyword(@PathVariable Long keywordId) {
        contentService.deleteKeyword(keywordId);
        return ApiResponse.success(null, "키워드가 삭제되었습니다.");
    }
}
