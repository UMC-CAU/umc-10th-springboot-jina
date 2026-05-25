package com.example.umc.domain.member.dto;

import com.example.umc.domain.member.enums.Address;
import com.example.umc.domain.member.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    // 회원가입 API에서 받을 요청 값.
    // 이번 Spring Security 미션 때문에 폼 로그인에 사용할 email/password를 추가.
    public record SignUp(
            // @Email: 이메일 형식인지 검사
            @Email
            @NotBlank
            String email,

            // 비밀번호 원문. DB에는 이 값 그대로 저장하지 않고, Service에서 BCrypt로 암호화해서 저장해야함.
            @NotBlank
            String password,

            @NotBlank
            String name,

            @NotNull
            Gender gender,

            @NotNull
            LocalDate birth,

            @NotNull
            Address address,
            String region,

            // 아래 3개는 필수 약관입니다. Service에서 true인지 한 번 더 확인.
            @NotNull
            Boolean ageConfirm,       // 연령 확인

            @NotNull
            Boolean serviceAgree,     // 서비스 이용약관

            @NotNull
            Boolean privacyAgree,     // 개인정보 수집 및 이용

            Boolean locationAgree,    // 위치정보 이용
            Boolean marketingAgree,

            List<Long> preferenceFoods
    ){}

    // 로그인 API에서 받을 요청 값.
    // 회원가입 때 저장한 email/password 조합으로 JWT 토큰을 발급받기 위해 사용.
    public record Login(
            @Email
            @NotBlank
            String email,

            @NotBlank
            String password
    ){}
}
