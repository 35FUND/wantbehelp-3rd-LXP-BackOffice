package com.shortudy.backoffice.domain.content.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 임시 자동 검수 폴링 스케줄러
 * TODO: 업로드 완료 이벤트 기반 파이프라인으로 전환되면 제거한다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ShortsInspectionPollingScheduler {

    private final ShortsReviewService shortsReviewService;

    @Value("${app.ai-inspection.polling-enabled:true}")
    private boolean pollingEnabled;

    @Value("${app.ai-inspection.polling-batch-size:3}")
    private int pollingBatchSize;

    @Scheduled(
            fixedDelayString = "${app.ai-inspection.polling-interval-ms:5000}",
            initialDelayString = "${app.ai-inspection.polling-initial-delay-ms:10000}"
    )
    public void pollAndInspect() {
        if (!pollingEnabled) {
            return;
        }

        List<Long> targetShortsIds = shortsReviewService.findPendingInspectionTargets(pollingBatchSize);
        if (targetShortsIds.isEmpty()) {
            return;
        }

        log.info("[TEMP-POLLING] 자동 검수 대상 {}건 감지: {}", targetShortsIds.size(), targetShortsIds);

        for (Long shortsId : targetShortsIds) {
            try {
                shortsReviewService.triggerInspection(shortsId);
            } catch (Exception e) {
                log.error("[TEMP-POLLING] shortsId={} 자동 검수 실패", shortsId, e);
                shortsReviewService.markAsAiCheckAfterFailedInspection(shortsId);
                log.warn("[TEMP-POLLING] shortsId={} 실패 건을 AI_CHECK로 전환해 재시도 루프를 차단합니다.", shortsId);
            }
        }
    }
}
