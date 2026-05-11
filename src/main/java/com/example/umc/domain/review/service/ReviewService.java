package com.example.umc.domain.review.service;

import com.example.umc.domain.member.entity.Member;
import com.example.umc.domain.member.exception.MemberException;
import com.example.umc.domain.member.exception.code.MemberErrorCode;
import com.example.umc.domain.member.repository.MemberRepository;
import com.example.umc.domain.review.converter.ReviewConverter;
import com.example.umc.domain.review.dto.ReviewReqDTO;
import com.example.umc.domain.review.dto.ReviewResDTO;
import com.example.umc.domain.review.entity.Review;
import com.example.umc.domain.review.repository.ReviewRepository;
import com.example.umc.domain.store.entity.Store;
import com.example.umc.domain.store.exception.StoreException;
import com.example.umc.domain.store.exception.code.StoreErrorCode;
import com.example.umc.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;

    public ReviewResDTO.PostReview postReview(String token, ReviewReqDTO.PostReview dto){
        return ReviewConverter.toPostReview();
    }

    public Review createReview(Long storeId, ReviewReqDTO.ReviewCreate request) {

        // [1] 세 번째 물음표(?) : member_id (users_id) 에 넣을 사람을 DB에서 데려옵니다.
        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        // [2] 네 번째 물음표(?) : store_id (restaurant_id) 에 넣을 식당을 DB에서 데려옵니다.
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreException(StoreErrorCode.NOT_FOUND));

        // [3] 첫 번째(content), 두 번째(rating) 물음표(?)를 포함하여 최종 쿼리문을 쏠 준비를 합니다. (Converter가 조립해 줌)
        /*
           Converter 내부에서는 이렇게 작동하고 있습니다:
           Review.builder()
                 .body(request.body())     // 👈 content (?)
                 .rating(request.rating()) // 👈 rating (?)
                 .member(member)           // 👈 member_id (?)
                 .store(store)             // 👈 store_id (?)
                 .build();
        */
        Review newReview = ReviewConverter.toReview(request, member, store);

        // [4] 실행!
        // 💡 JPA가 위에서 조립된 newReview를 보고
        // INSERT INTO review (content, rating, member_id, store_id) VALUES (?, ?, ?, ?)
        // 쿼리를 빵! 하고 날려줍니다.
        return reviewRepository.save(newReview);
    }
}
