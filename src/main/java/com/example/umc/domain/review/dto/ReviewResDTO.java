package com.example.umc.domain.review.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public class ReviewResDTO {

    @Builder
    public record PostReview(
            Long reviewId,
            LocalDateTime createdAt
    ){}

    @Builder
    public record ReviewCreateResult(
            Long reviewId,
            LocalDateTime createdAt
    ){}

    @Builder
    public record MyReviewDTO(
            Long reviewId,
            String storeName,
            Integer rating,
            String text,
            LocalDateTime createdAt
    ){}

    @Builder
    public record MyReviewListDTO(
            List<MyReviewDTO> reviews,
            String nextCursor,
            Boolean hasNext,
            Integer size,
            String sortType
    ){}
}
