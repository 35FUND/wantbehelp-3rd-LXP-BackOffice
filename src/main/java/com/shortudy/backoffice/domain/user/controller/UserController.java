package com.shortudy.backoffice.domain.user.controller;

import com.shortudy.backoffice.domain.user.dto.request.UpdateRequest;
import com.shortudy.backoffice.domain.user.dto.response.UserProjection;
import com.shortudy.backoffice.domain.user.service.UserService;
import com.shortudy.backoffice.global.common.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 유저 관리 API 컨트롤러
 * 담당자: 진용
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 전체 유저 목록 조회(active, inactive ,deleted)
     */
    @GetMapping
    public ApiResponse<List<UserProjection>> getUsers() {
        return ApiResponse.success(userService.findAllUsers(), "유저 목록 조회 성공");
    }

    /**
     * 특정 유저 정보 수정 (유저 상태 ACTIVE, INACTIVE, DELETED)
     */
    @PatchMapping("/{memberId}")
    public ApiResponse<Void> updateMember(@PathVariable Long userId, @RequestBody UpdateRequest updateRequest) {
        userService.updateMember(userId, updateRequest);
        return ApiResponse.success(null, "유저 정보가 수정되었습니다.");
    }
}
