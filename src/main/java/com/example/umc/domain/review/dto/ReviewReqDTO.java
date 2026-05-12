package com.example.umc.domain.review.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class ReviewReqDTO {
    public record PostReview(
            Integer rating,
            Long storeId,
            String text,
            List<String> images
    ){}

    public record ReviewCreate(
            @NotNull(message = "사용자 ID는 필수입니다.")
            Long memberId,

            @NotNull(message = "별점은 필수입니다.")
            Integer rating,

            @NotBlank(message = "리뷰 내용은 비어 있을 수 없습니다.")
            String text
    ){}

    public record MyReviewListRequest(
            Long memberId,
            String cursor,
            Integer size,
            String sortType
    ){}
}
