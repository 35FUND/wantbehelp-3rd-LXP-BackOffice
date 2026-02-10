package com.shortudy.backoffice.domain.content.repository;

import com.shortudy.backoffice.domain.content.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
