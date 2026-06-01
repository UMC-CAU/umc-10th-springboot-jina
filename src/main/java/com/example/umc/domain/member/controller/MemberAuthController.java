package com.example.umc.domain.member.controller;

import com.example.umc.domain.member.dto.MemberReqDTO;
import com.example.umc.domain.member.dto.MemberResDTO;
import com.example.umc.domain.member.service.MemberService;
import com.example.umc.global.apiPayload.ApiResponse;
import com.example.umc.global.apiPayload.code.BaseSuccessCode;
import com.example.umc.global.apiPayload.code.GeneralSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth") // auth용 컨트롤러 따로.
public class MemberAuthController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ApiResponse<MemberResDTO.SignUp> signUp(
            // reqDTO에 검증 있었으니 @Valid 추가
            //@RequestBody가 알아서 DTO형으로 바꿔줌
            @RequestBody @Valid MemberReqDTO.SignUp dto
    ){
        BaseSuccessCode code = GeneralSuccessCode.OK;
        return ApiResponse.onSuccess(code, memberService.getSignUp(dto));
    }

    @PostMapping("/login")
    public ApiResponse<MemberResDTO.Login> login(
            // email/password를 DTO로 받은 뒤 Service에서 실제 비밀번호 검증과 JWT 발급을 처리.
            @RequestBody @Valid MemberReqDTO.Login dto
    ) {
        BaseSuccessCode code = GeneralSuccessCode.OK;
        return ApiResponse.onSuccess(code, memberService.login(dto));
    }
}
