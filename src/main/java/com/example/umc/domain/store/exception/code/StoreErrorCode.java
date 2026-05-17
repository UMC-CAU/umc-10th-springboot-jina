package com.example.umc.domain.store.exception.code;

import com.example.umc.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum StoreErrorCode implements BaseErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER404_1", "해당 가게가 존재하지 않습니다.");

    private final HttpStatus status; // BaseErrorCode의 getStatus()와 매칭
    private final String code;       // BaseErrorCode의 getCode()와 매칭
    private final String message;    // BaseErrorCode의 getMessage()와 매칭
}
