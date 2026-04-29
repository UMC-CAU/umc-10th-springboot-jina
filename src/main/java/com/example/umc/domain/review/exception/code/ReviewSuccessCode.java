package com.example.umc.domain.review.exception.code;

import com.example.umc.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReviewSuccessCode implements BaseSuccessCode {

    REVIEW_POST_SUCCESS(HttpStatus.OK, "REVIEW200_1", "리뷰가 성공적으로 등록되었습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
