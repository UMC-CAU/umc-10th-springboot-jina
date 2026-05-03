package com.example.umc.domain.member.dto;

import com.example.umc.domain.member.enums.Address;
import com.example.umc.domain.member.enums.Gender;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

public class MemberReqDTO {
    //Request Body 예시
    public record RequestBody(
            String stringTest,
            Long longTest
    ){}

    //public static class
    @Getter
    public static class RequestBodyClass{
        private String stringTest;
        private long longTest;
    }

    public record GetInfo(
            Long id
    ){}

    public record SignUp(
            String name,
            Gender gender,
            LocalDate birth,
            Address address,

            Boolean ageConfirm,       // 연령 확인
            Boolean serviceAgree,     // 서비스 이용약관
            Boolean privacyAgree,     // 개인정보 수집 및 이용
            Boolean locationAgree,    // 위치정보 이용
            Boolean marketingAgree,

            List<Long> preferenceFoods
    ){}
}
