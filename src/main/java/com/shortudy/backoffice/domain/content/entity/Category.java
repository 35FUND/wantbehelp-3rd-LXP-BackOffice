package com.shortudy.backoffice.domain.content.entity;

import com.shortudy.backoffice.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 콘텐츠 카테고리 엔티티
 * 담당: 승훈
 */
@Entity
@Getter
@Table(name = "categories")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Builder
    public Category(String name) {
        this.name = name;
    }

    public void updateName(String name) {
        this.name = name;
    }
}
