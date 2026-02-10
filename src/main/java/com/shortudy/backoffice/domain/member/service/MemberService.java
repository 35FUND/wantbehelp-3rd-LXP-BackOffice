package com.shortudy.backoffice.domain.member.service;

import com.shortudy.backoffice.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 회원 관리 서비스
 * 담당자: 진용
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    /**
     * 유저 목록 조회
     */
    public Object findAll() {
        return memberRepository.findAll();
    }

    /**
     * 유저 정보 수정 (추가 예정)
     */
    @Transactional
    public void updateMember(Long memberId, Object updateRequest) {
        // TODO: 유저 정보 수정 로직 구현
    }
}
