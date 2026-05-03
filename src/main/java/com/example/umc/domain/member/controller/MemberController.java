package com.example.umc.domain.member.controller;

import com.example.umc.domain.member.dto.MemberReqDTO;
import com.example.umc.domain.member.dto.MemberResDTO;
import com.example.umc.domain.member.service.MemberService;
import com.example.umc.global.apiPayload.ApiResponse;
import com.example.umc.global.apiPayload.code.BaseSuccessCode;
import com.example.umc.global.apiPayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    /*@PostMapping("v1/users/me")
    public ApiResponse<MemberResDTO.GetInfo> getInfo(
            @RequestBody MemberReqDTO.GetInfo dto
    ){
        BaseSuccessCode code = MemberSuccessCode.OK;
        return ApiResponse.onSuccess(code, memberService.getInfo(dto));
    }*/
    @GetMapping("/home")// get이라 req없음 즉 파라미터 x
    public ApiResponse<MemberResDTO.Home> home(
            @RequestParam(name = "memberId") Long memberId
    ){
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, memberService.getHome(memberId));
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