package com.shortudy.backoffice.domain.content.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * AI 영상 검수 결과 엔티티
 */
@Entity
@Getter
@Table(name = "shorts_inspection_results")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShortsInspectionResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "shorts_id", nullable = false)
    private Long shortsId;

    @Column(name = "inspection_status", nullable = false)
    private String inspectionStatus;

    @Column(nullable = false)
    private String category;

    @Column(name = "confidence_score", nullable = false)
    private Double confidenceScore;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String reason;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public ShortsInspectionResult(
            Long shortsId,
            String inspectionStatus,
            String category,
            Double confidenceScore,
            String reason,
            LocalDateTime createdAt
    ) {
        this.shortsId = shortsId;
        this.inspectionStatus = inspectionStatus;
        this.category = category;
        this.confidenceScore = confidenceScore;
        this.reason = reason;
        this.createdAt = createdAt;
    }
}
