package com.shortudy.backoffice.domain.content.service;

import com.shortudy.backoffice.domain.content.repository.CategoryRepository;
import com.shortudy.backoffice.domain.content.repository.KeywordRepository;
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
        // TODO: 카테고리 생성 및 저장
    }

    /**
     * 키워드 추가
     */
    @Transactional
    public void addKeyword(String name) {
        // TODO: 키워드 생성 및 저장
    }
}
