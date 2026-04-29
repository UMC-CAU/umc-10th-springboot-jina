package com.example.umc.domain.member.exception.code;

import com.example.umc.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements BaseErrorCode {

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER404_1", "해당 사용자가 존재하지 않습니다."),
    MEMBER_ALREADY_EXISTS(HttpStatus.CONFLICT, "MEMBER409_1", "이미 존재하는 회원입니다."),
    INVALID_MEMBER_ID(HttpStatus.BAD_REQUEST, "MEMBER400_1", "잘못된 사용자 ID입니다."),
    TERM_NOT_AGREED(HttpStatus.BAD_REQUEST, "MEMBER400_2", "필수 약관에 동의해야 합니다."),
    HOME_DATA_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER404_2", "홈화면 정보를 불러올 수 없습니다."),
    MISSION_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER404_3", "조회 가능한 미션이 없습니다.");

    private final HttpStatus status; // BaseErrorCode의 getStatus()와 매칭
    private final String code;       // BaseErrorCode의 getCode()와 매칭
    private final String message;    // BaseErrorCode의 getMessage()와 매칭


}