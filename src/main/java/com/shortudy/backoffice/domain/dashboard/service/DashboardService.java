package com.shortudy.backoffice.domain.dashboard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 대시보드 통계 서비스
 * 담당: 세훈(가입자), 민수(업로드/전환율)
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardService {

    /**
     * 가입자 수 및 신규 가입 추이 조회 (세훈)
     */
    public Object getSubscriberStats() {
        // TODO: 가입자 추이 통계 쿼리 구현
        return null;
    }

    /**
     * 일 단위 업로드 수 조회 (민수)
     */
    public Object getUploadStats() {
        // TODO: 일별 업로드 수 통계 쿼리 구현
        return null;
    }

    /**
     * 게시 전환율 조회 (민수)
     */
    public Object getConversionRate() {
        // TODO: 전체 대비 게시 전환율 계산 로직 구현
        return null;
    }
}
