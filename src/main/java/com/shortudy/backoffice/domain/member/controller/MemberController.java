package com.shortudy.backoffice.domain.member.controller;

import com.shortudy.backoffice.domain.member.service.MemberService;
import com.shortudy.backoffice.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 유저 관리 API 컨트롤러
 * 담당자: 진용
 */
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 전체 유저 목록 조회
     */
    @GetMapping
    public ApiResponse<Object> getMembers() {
        return ApiResponse.success(memberService.findAll(), "유저 목록 조회 성공");
    }

    /**
     * 특정 유저 정보 수정
     */
    @PatchMapping("/{memberId}")
    public ApiResponse<Void> updateMember(@PathVariable Long memberId, @RequestBody Object updateRequest) {
        memberService.updateMember(memberId, updateRequest);
        return ApiResponse.success(null, "유저 정보가 수정되었습니다.");
    }
}
