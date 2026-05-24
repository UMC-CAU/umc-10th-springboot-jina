package com.example.umc.domain.member.repository;

import com.example.umc.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    void deleteById(Long memberId);

    //로그인한 회원 찾는 용도
    Optional<Member> findByEmail(String email);

    // Spring Data JPA가 메서드 이름을 보고 자동으로 쿼리.
    // 회원가입할 때 같은 이메일이 이미 DB에 있는지 확인하기 위해 사용합.
    boolean existsByEmail(String email);

}
