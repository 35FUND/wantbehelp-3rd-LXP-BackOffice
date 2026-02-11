package com.shortudy.backoffice.domain.content.repository;

import com.shortudy.backoffice.domain.content.entity.Category;
import com.shortudy.backoffice.domain.content.entity.ShortsStatus;
import com.shortudy.backoffice.domain.dashboard.dto.projection.CategoryShortsCountView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);

    // 카테고리 관리 화면 조회용
    java.util.List<Category> findAllByOrderByNameAsc();

    @Query("""
            select c.id as categoryId,
                   c.name as categoryName,
                   count(s) as shortsCount
            from Category c
            left join Shorts s on s.categoryId = c.id and s.status = :status
            group by c.id, c.name
            order by shortsCount desc
            """)
    List<CategoryShortsCountView> findCategoryShortsCounts(@Param("status") ShortsStatus status);
}
