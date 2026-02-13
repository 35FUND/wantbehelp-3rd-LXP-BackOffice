package com.shortudy.backoffice.domain.content.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 콘텐츠 키워드 엔티티
 * 담당: 승훈
 */
@Entity
@Getter
@Table(name = "keyword")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "display_name", nullable = false, length = 50)
    private String displayName;

    @Column(name = "normalized_name", nullable = false, length = 50, unique = true)
    private String normalizedName;

    @Builder
    public Keyword(String displayName, String normalizedName) {
        this.displayName = displayName;
        this.normalizedName = normalizedName;
    }

    public void updateDisplayName(String displayName, String normalizedName) {
        this.displayName = displayName;
        this.normalizedName = normalizedName;
    }
}
