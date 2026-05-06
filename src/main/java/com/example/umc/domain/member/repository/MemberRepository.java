package com.example.umc.domain.member.repository;

import com.example.umc.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    void deleteById(Long memberId);

    //마이페이지 조회같은 경우는 member관련 내용이기때문에 findById면 됨 즉, 필요없음.
}
