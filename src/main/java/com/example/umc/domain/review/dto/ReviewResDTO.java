package com.example.umc.domain.review.dto;

import lombok.Builder;

import java.time.LocalDateTime;

public class ReviewResDTO {

    @Builder
    public record PostReview(
            Long reviewId,
            LocalDateTime createdAt
    ){}
}
