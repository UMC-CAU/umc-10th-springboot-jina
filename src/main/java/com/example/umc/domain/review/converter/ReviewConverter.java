package com.example.umc.domain.review.converter;

import com.example.umc.domain.member.entity.Member;
import com.example.umc.domain.review.dto.ReviewReqDTO;
import com.example.umc.domain.review.dto.ReviewResDTO;
import com.example.umc.domain.review.entity.Review;
import com.example.umc.domain.store.entity.Store;

import java.time.LocalDateTime;

public class ReviewConverter {
    public static ReviewResDTO.PostReview toPostReview(){
        return ReviewResDTO.PostReview.builder()
                .reviewId(7L)
                .createdAt(LocalDateTime.now())
                .build();
    }

    // 1. DTO -> Entity 변환 (DB 저장용 조립)
    public static Review toReview(ReviewReqDTO.ReviewCreate request, Member member, Store store) {
        return Review.builder()
                .rating(request.rating())
                .reviewText(request.text())
                .member(member)
                .store(store)
                .build();
    }

    // 2. Entity -> DTO 변환 (클라이언트 응답용)
    public static ReviewResDTO.ReviewCreateResult toReviewCreateResult(Review review) {
        // record는 생성자를 자동으로 만들어주므로 바로 new 키워드 사용!
        return new ReviewResDTO.ReviewCreateResult(
                review.getId(),
                review.getCreatedAt()
        );
    }
}
