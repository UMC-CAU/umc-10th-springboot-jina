package com.example.umc.domain.store.exception;

import com.example.umc.global.apiPayload.code.BaseErrorCode;
import com.example.umc.global.apiPayload.exception.ProjectException;

public class StoreException extends ProjectException {
    public StoreException(BaseErrorCode errorCode) {
        super(errorCode);
    }

}
