package com.shortudy.backoffice.domain.content.service;

import com.shortudy.backoffice.domain.content.entity.Category;
import com.shortudy.backoffice.domain.content.entity.Keyword;
import com.shortudy.backoffice.domain.content.repository.CategoryRepository;
import com.shortudy.backoffice.domain.content.repository.KeywordRepository;
import com.shortudy.backoffice.global.error.BaseException;
import com.shortudy.backoffice.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 콘텐츠 관리 서비스
 * 담당: 승훈
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContentService {

    private final CategoryRepository categoryRepository;
    private final KeywordRepository keywordRepository;

    /**
     * 카테고리별 콘텐츠 분포 조회
     */
    public Object getContentDistribution() {
        // TODO: 카테고리별 콘텐츠 카운트 로직 구현
        return null;
    }

    /**
     * 카테고리 추가
     */
    @Transactional
    public void addCategory(String name) {
        String trimmedName = normalizeName(name);
        if (categoryRepository.existsByName(trimmedName)) {
            throw new BaseException(ErrorCode.INVALID_INPUT_VALUE, "이미 존재하는 카테고리입니다.");
        }
        categoryRepository.save(Category.builder().name(trimmedName).build());
    }

    /**
     * 키워드 추가
     */
    @Transactional
    public void addKeyword(String name) {
        String displayName = normalizeName(name);
        String normalizedName = normalizeKeyword(displayName);
        if (keywordRepository.existsByNormalizedName(normalizedName)) {
            throw new BaseException(ErrorCode.INVALID_INPUT_VALUE, "이미 존재하는 키워드입니다.");
        }
        keywordRepository.save(Keyword.builder()
                .displayName(displayName)
                .normalizedName(normalizedName)
                .build());
    }

    /**
     * 카테고리 삭제
     */
    @Transactional
    public void deleteCategory(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new BaseException(ErrorCode.CONTENT_NOT_FOUND);
        }
        categoryRepository.deleteById(categoryId);
    }

    /**
     * 키워드 삭제
     */
    @Transactional
    public void deleteKeyword(Long keywordId) {
        if (!keywordRepository.existsById(keywordId)) {
            throw new BaseException(ErrorCode.CONTENT_NOT_FOUND);
        }
        keywordRepository.deleteById(keywordId);
    }

    private String normalizeName(String name) {
        if (name == null) {
            throw new BaseException(ErrorCode.INVALID_INPUT_VALUE);
        }
        String trimmed = name.trim();
        if (trimmed.isEmpty()) {
            throw new BaseException(ErrorCode.INVALID_INPUT_VALUE);
        }
        return trimmed;
    }

    private String normalizeKeyword(String displayName) {
        return displayName
                .toLowerCase()
                .replaceAll("\\s+", " ")
                .trim();
    }
}
