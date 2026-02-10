package com.shortudy.backoffice.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shortudy.backoffice.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
