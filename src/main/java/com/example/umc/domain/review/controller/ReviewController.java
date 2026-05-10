package com.example.umc.domain.review.controller;

import com.example.umc.domain.review.converter.ReviewConverter;
import com.example.umc.domain.review.dto.ReviewReqDTO;
import com.example.umc.domain.review.dto.ReviewResDTO;
import com.example.umc.domain.review.entity.Review;
import com.example.umc.domain.review.service.ReviewService;
import com.example.umc.global.apiPayload.ApiResponse;
import com.example.umc.global.apiPayload.code.BaseSuccessCode;
import com.example.umc.global.apiPayload.code.GeneralSuccessCode;
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
        BaseSuccessCode code = GeneralSuccessCode.OK;
        return ApiResponse.onSuccess(code, reviewService.postReview(token, dto));
    }


    @PostMapping("/stores/{storeId}/reviews")
    public ApiResponse<ReviewResDTO.ReviewCreateResult> createReview(
            @PathVariable(name = "storeId") Long storeId,
            @RequestBody ReviewReqDTO.ReviewCreate request) {

        Review review = reviewService.createReview(storeId, request);

        BaseSuccessCode code = GeneralSuccessCode.OK;
        return ApiResponse.onSuccess(code, ReviewConverter.toReviewCreateResult(review));
    }
}
