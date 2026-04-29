package com.example.umc.domain.mission.exception.code;

import com.example.umc.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@Getter
@RequiredArgsConstructor
public enum MissionSuccessCode implements BaseSuccessCode {
    MISSION_SUCCESS_REQUEST(HttpStatus.OK, "MISSION200_1", "미션 성공 요청 완료");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
