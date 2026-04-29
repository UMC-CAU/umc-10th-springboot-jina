package com.example.umc.domain.review.controller;

import com.example.umc.domain.member.dto.MemberReqDTO;
import com.example.umc.domain.member.dto.MemberResDTO;
import com.example.umc.domain.member.exception.code.MemberSuccessCode;
import com.example.umc.domain.review.dto.ReviewReqDTO;
import com.example.umc.domain.review.dto.ReviewResDTO;
import com.example.umc.domain.review.exception.code.ReviewSuccessCode;
import com.example.umc.domain.review.service.ReviewService;
import com.example.umc.global.apiPayload.ApiResponse;
import com.example.umc.global.apiPayload.code.BaseSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/reviews")
    public ApiResponse<ReviewResDTO.PostReview> postReview(
            @RequestHeader("Authorization") String token,
            @RequestBody ReviewReqDTO.PostReview dto
    ){
        BaseSuccessCode code = ReviewSuccessCode.REVIEW_POST_SUCCESS;
        return ApiResponse.onSuccess(code, reviewService.postReview(token, dto));
    }

}
