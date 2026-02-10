package com.shortudy.backoffice.domain.auth.repository;

import com.shortudy.backoffice.domain.auth.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 관리자 저장소 인터페이스
 */
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByEmail(String email);
}
