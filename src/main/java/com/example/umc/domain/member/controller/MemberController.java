package com.example.umc.domain.member.controller;

import com.example.umc.domain.member.dto.MemberReqDTO;
import com.example.umc.domain.member.dto.MemberResDTO;
import com.example.umc.domain.member.service.MemberService;
import com.example.umc.global.apiPayload.ApiResponse;
import com.example.umc.global.apiPayload.code.BaseSuccessCode;
import com.example.umc.global.apiPayload.code.GeneralSuccessCode;
import com.example.umc.global.security.entity.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/v1/users/me")
    public ApiResponse<MemberResDTO.GetInfo> getInfo(
            // JwtAuthFilter가 토큰을 검증한 뒤 SecurityContextHolder에 넣어둔 현재 로그인 회원입니다.
            // 그래서 마이페이지는 더 이상 memberId를 요청으로 받지 않고, 토큰 주인 정보를 사용합니다.
            @AuthenticationPrincipal AuthMember member)
    {
        BaseSuccessCode code = GeneralSuccessCode.OK;
        return ApiResponse.onSuccess(code, memberService.getInfo(member));
    }

    @GetMapping("/home")// get이라 req없음 즉 파라미터 x
    public ApiResponse<MemberResDTO.Home> home(
            @RequestParam(name = "memberId") Long memberId,
            @RequestParam(name = "cursor", required = false) String cursor,

            // 💡 한 번에 몇 개의 미션을 보여줄지 (기본 10개)
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size

    ){
        MemberResDTO.Home result = memberService.getHome(memberId, cursor, size);
        BaseSuccessCode code = GeneralSuccessCode.OK;
        return ApiResponse.onSuccess(code, result);
    }



    // 아무것도 받지 않은 경우
    @GetMapping("/test")
    public String test(){
        return "test";
    }

    // Query Parameter
    @PostMapping("/query-parameter")
    public ApiResponse<String> exception(
            @RequestParam String queryParameter
    ){
        BaseSuccessCode code = GeneralSuccessCode.OK;
        return ApiResponse.onSuccess(code, memberService.singleParameter(queryParameter));
    }

    // Request Body
    @PostMapping("/request-body")
    public ApiResponse<MemberResDTO.RequestBody> requestBody(
            @RequestBody MemberReqDTO.RequestBody dto
    ){
        BaseSuccessCode code = GeneralSuccessCode.OK;
        return ApiResponse.onSuccess(code,memberService.requestBody(dto));
    }

    // Path Variable
    @PostMapping("/{pathVariable}")
    public String pathVariable(
            @PathVariable String pathVariable
    ){
        return memberService.singleParameter(pathVariable);
    }

    // Header
    @PostMapping("/header")
    public String header(
            @RequestHeader("test") String test
    ){
        return memberService.singleParameter(test); // 이미지상에는 return값이 잘려있지만 구조상 문자열을 반환합니다.
    }
}
