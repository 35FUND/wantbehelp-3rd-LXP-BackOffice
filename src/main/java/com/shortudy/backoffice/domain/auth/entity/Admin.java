package com.shortudy.backoffice.domain.auth.entity;

import com.shortudy.backoffice.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 백오피스 관리자 엔티티
 */
@Entity
@Getter
@Table(name = "admins")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Builder
    public Admin(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
}
