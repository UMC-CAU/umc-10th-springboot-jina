package com.example.umc.domain.review.exception.code;

import com.example.umc.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReviewErrorCode implements BaseErrorCode {
    REVIEW_NOT_POST(HttpStatus.BAD_REQUEST, "REVIEW400_1", "리뷰 게시글 형식이 올바르지 않습니다.");

    private final HttpStatus status; // BaseErrorCode의 getStatus()와 매칭
    private final String code;       // BaseErrorCode의 getCode()와 매칭
    private final String message;    // BaseErrorCode의 getMessage()와 매칭

}
