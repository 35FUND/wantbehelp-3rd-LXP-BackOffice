package com.shortudy.backoffice.domain.content.entity;

import com.shortudy.backoffice.global.common.BaseEntity;
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
@Table(name = "keywords")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Keyword extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Builder
    public Keyword(String name) {
        this.name = name;
    }

    public void updateName(String name) {
        this.name = name;
    }
}
