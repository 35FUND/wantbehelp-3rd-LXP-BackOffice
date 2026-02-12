package com.shortudy.backoffice.domain.content.entity;

import com.shortudy.backoffice.global.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 기존 서비스의 shorts 테이블 매핑 엔티티
 * 대시보드 통계 조회에 필요한 필드 중심으로 구성
 */
@Entity
@Getter
@Table(name = "shorts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Shorts extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "video_url", length = 500)
    private String videoUrl;

    @Column(name = "thumbnail_url", length = 500)
    private String thumbnailUrl;

    @Column(name = "duration_sec")
    private Integer durationSec;

    @Column(name = "like_count", nullable = false)
    private Integer likeCount;

    @Column(name = "view_count", nullable = false)
    private Long viewCount;

    @Column(name = "published_at")
    // 게시 시각 (PUBLISHED 전환 시점)
    private LocalDateTime publishedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    // DB enum 문자열(REJECT, PUBLISHED, AI_CHECK, PENDING)로 저장
    private ShortsStatus status;

    @Builder
    public Shorts(Long userId,
                  Long categoryId,
                  String title,
                  String description,
                  String videoUrl,
                  String thumbnailUrl,
                  Integer durationSec,
                  Integer likeCount,
                  Long viewCount,
                  LocalDateTime publishedAt,
                  ShortsStatus status) {
        this.userId = userId;
        this.categoryId = categoryId;
        this.title = title;
        this.description = description;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.durationSec = durationSec;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.publishedAt = publishedAt;
        this.status = status;
    }

    /**
     * 검수 상태를 변경한다.
     */
    public void changeStatus(ShortsStatus status) {
        this.status = status;

        if (status == ShortsStatus.PUBLISHED) {
            if (this.publishedAt == null) {
                this.publishedAt = LocalDateTime.now();
            }
            return;
        }

        this.publishedAt = null;
    }

    /**
     * AI 검수 진행 상태로 전환한다.
     */
    public void markAiCheck() {
        this.status = ShortsStatus.AI_CHECK;
    }
}
