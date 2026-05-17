package com.example.umc.domain.review.repository;

import com.example.umc.domain.review.entity.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository <Review, Long> {

    @Query("SELECT r FROM Review r " +
            "JOIN FETCH r.store s " +
            "WHERE r.member.id = :memberId " +
            "AND (:cursorId IS NULL OR r.id < :cursorId) " +
            "ORDER BY r.id DESC")
    Slice<Review> findMyReviewsOrderById(
            @Param("memberId") Long memberId,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );

    @Query("SELECT r FROM Review r " +
            "JOIN FETCH r.store s " +
            "WHERE r.member.id = :memberId " +
            "AND (:cursorRating IS NULL " +
            "OR r.rating < :cursorRating " +
            "OR (r.rating = :cursorRating AND r.id < :cursorId)) " +
            "ORDER BY r.rating DESC, r.id DESC")
    Slice<Review> findMyReviewsOrderByRating(
            @Param("memberId") Long memberId,
            @Param("cursorRating") Integer cursorRating,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );
}
