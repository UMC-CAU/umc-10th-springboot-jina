package com.example.umc.domain.member.exception.code;

import com.example.umc.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberSuccessCode implements BaseSuccessCode {

    OK(HttpStatus.OK, "MEMBER200_1", "성공적으로 유저를 조회했습니다."),
    JOIN_SUCCESS(HttpStatus.OK,"MEMBER200_2", "회원가입이 완료되었습니다."),
    HOME_SUCCESS(HttpStatus.OK, "MEMBER200_3", "홈화면 조회에 성공했습니다."),
    MISSION_LIST_SUCCESS(HttpStatus.OK, "MEMBER200_4", "미션목록 조회 성공");


    private final HttpStatus status;
    private final String code;
    private final String message;
}
