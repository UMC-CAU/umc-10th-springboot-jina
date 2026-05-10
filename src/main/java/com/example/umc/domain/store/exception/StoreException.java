package com.example.umc.domain.store.exception;

import com.example.umc.global.apiPayload.code.BaseErrorCode;

public class StoreException extends RuntimeException {
    public StoreException(BaseErrorCode errorCode) {
        super(errorCode.getMessage());
    }

}
