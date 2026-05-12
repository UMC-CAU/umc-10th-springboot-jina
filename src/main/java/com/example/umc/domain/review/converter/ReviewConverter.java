package com.example.umc.domain.review.converter;

import com.example.umc.domain.member.entity.Member;
import com.example.umc.domain.review.dto.ReviewReqDTO;
import com.example.umc.domain.review.dto.ReviewResDTO;
import com.example.umc.domain.review.entity.Review;
import com.example.umc.domain.store.entity.Store;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    public static ReviewResDTO.MyReviewDTO toMyReviewDTO(Review review) {
        return ReviewResDTO.MyReviewDTO.builder()
                .reviewId(review.getId())
                .storeName(review.getStore().getName())
                .rating(review.getRating())
                .text(review.getReviewText())
                .createdAt(review.getCreatedAt())
                .build();
    }

    public static ReviewResDTO.MyReviewListDTO toMyReviewListDTO(
            Slice<Review> reviewSlice,
            String sortType
    ) {
        List<ReviewResDTO.MyReviewDTO> reviews = reviewSlice.getContent().stream()
                .map(ReviewConverter::toMyReviewDTO)
                .collect(Collectors.toList());

        String nextCursor = null;
        if (!reviewSlice.getContent().isEmpty()) {
            Review lastReview = reviewSlice.getContent().get(reviewSlice.getContent().size() - 1);
            nextCursor = createNextCursor(lastReview, sortType);
        }

        return ReviewResDTO.MyReviewListDTO.builder()
                .reviews(reviews)
                .nextCursor(nextCursor)
                .hasNext(reviewSlice.hasNext())
                .size(reviewSlice.getSize())
                .sortType(sortType)
                .build();
    }

    private static String createNextCursor(Review review, String sortType) {
        if ("RATING".equalsIgnoreCase(sortType)) {
            return review.getRating() + ":" + review.getId();
        }

        return String.valueOf(review.getId());
    }
}
