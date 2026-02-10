package com.shortudy.backoffice.domain.user.repository;

import com.shortudy.backoffice.domain.user.dto.response.UserProjection;
import com.shortudy.backoffice.domain.user.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import com.shortudy.backoffice.domain.user.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u.email as email, u.nickname as nickname, u.role as role, " +
            "u.status as status, r.updatedAt as updatedAt " +
            "FROM User u " +
            "LEFT JOIN RefreshToken r ON u.id = r.userId " +
            "WHERE u.role = :role")
    List<UserProjection> findAllUserDetailForAdmin(@Param("role") UserRole role);

    Optional<User> findByEmail(String email);

}
