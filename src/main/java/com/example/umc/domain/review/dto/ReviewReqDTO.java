package com.example.umc.domain.review.dto;

import java.util.List;

public class ReviewReqDTO {
    public record PostReview(
            Integer rating,
            Long storeId,
            String text,
            List<String> images
    ){}
}
