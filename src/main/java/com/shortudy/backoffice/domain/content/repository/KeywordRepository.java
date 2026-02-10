package com.shortudy.backoffice.domain.content.repository;

import com.shortudy.backoffice.domain.content.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    boolean existsByName(String name);
}
