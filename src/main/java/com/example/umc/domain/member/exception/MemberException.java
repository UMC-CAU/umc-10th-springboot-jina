package com.example.umc.domain.member.exception;

import com.example.umc.global.apiPayload.code.BaseErrorCode;

public class MemberException extends RuntimeException {
    public MemberException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
