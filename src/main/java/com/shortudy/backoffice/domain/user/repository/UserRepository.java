package com.shortudy.backoffice.domain.user.repository;

import com.shortudy.backoffice.domain.dashboard.dto.projection.DailyUserCount;
import com.shortudy.backoffice.domain.user.dto.response.UserProjection;
import com.shortudy.backoffice.domain.user.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import com.shortudy.backoffice.domain.user.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {



    /*
    * [일별 사용자 가입 수 집계]
    * */
    @Query("SELECT FUNCTION('date', u.createdAt) as date, COUNT(u) as count " +
            "FROM User u " +
            "WHERE u.createdAt >= :from AND u.createdAt < :to " +
            "GROUP BY FUNCTION('date', u.createdAt)")
    List<DailyUserCount> countDailyUsers(@Param("from") LocalDateTime from,
                                         @Param("to") LocalDateTime to);

    @Query("SELECT u.email as email, u.nickname as nickname, u.role as role, u.role as userRole, " +
            "u.status as status, u.status as userStatus, r.updatedAt as updatedAt " +
            "FROM User u " +
            "LEFT JOIN RefreshToken r ON u.id = r.userId " +
            "WHERE u.role = :role")
    List<UserProjection> findAllUserDetailForAdmin(@Param("role") UserRole role);

    Optional<User> findByEmail(String email);

}
