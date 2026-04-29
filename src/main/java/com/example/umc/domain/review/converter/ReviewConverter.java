package com.example.umc.domain.review.converter;

import com.example.umc.domain.review.dto.ReviewResDTO;

import java.time.LocalDateTime;

public class ReviewConverter {
    public static ReviewResDTO.PostReview toPostReview(){
        return ReviewResDTO.PostReview.builder()
                .reviewId(7L)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
