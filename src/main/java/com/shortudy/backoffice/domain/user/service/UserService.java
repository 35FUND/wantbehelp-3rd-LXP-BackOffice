package com.shortudy.backoffice.domain.user.service;

import com.shortudy.backoffice.domain.user.dto.request.UpdateRequest;
import com.shortudy.backoffice.domain.user.entity.User;
import com.shortudy.backoffice.domain.user.entity.UserRole;
import com.shortudy.backoffice.domain.user.dto.response.UserProjection;
import com.shortudy.backoffice.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 회원 관리 서비스
 * 담당자: 진용
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 유저 목록 조회
     * 관리자를 제외한 유저 조회 (유저 이메일, 닉네임, Status, Role 까지)
     */
    @Transactional(readOnly = true)
    public List<UserProjection> findAllUsers() {

        List<UserProjection> userProjections = userRepository.findAllUserDetailForAdmin(UserRole.USER);

        return userProjections;
    }

    /**
     * 유저 정보 수정 (추가 예정)
     * 유저의 상태만 업데이트 가능
     */
    @Transactional
    public void updateMember(Long userId, UpdateRequest request) {

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        if (user.getStatus() == request.userStatus()) throw new IllegalArgumentException("업데이트 불가");

        user.updateUserStatus(request.userStatus());
    }
}
