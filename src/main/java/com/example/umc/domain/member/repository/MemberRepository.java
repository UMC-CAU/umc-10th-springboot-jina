package com.example.umc.domain.member.repository;

import com.example.umc.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    void deleteByName(String name);

}
