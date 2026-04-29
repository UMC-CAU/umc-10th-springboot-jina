package com.example.umc.domain.member.converter;

import com.example.umc.domain.member.dto.MemberResDTO;
import com.example.umc.domain.member.entity.Member;

public class MemberConverter {
    public static MemberResDTO.RequestBody toRequestBody(
            String stringTest,
            Long longTest
    ){
        return MemberResDTO.RequestBody.builder()
                .stringTest(stringTest)
                .longTest(longTest)
                .build();
    }
    // 마이페이지 응답 변환 로직
    public static MemberResDTO.GetInfo toGetInfo(
            Member member
    ){
        return MemberResDTO.GetInfo.builder()
                .email(member.getEmail())
                .name(member.getName())
                .point(member.getPoint())
                .phoneNumber(member.getPhoneNumber())
                .profileUrl(member.getProfileUrl())
                .build();
    }
}
