package com.example.umc.global.security.service;

import com.example.umc.domain.member.entity.Member;
import com.example.umc.domain.member.enums.SocialType;
import com.example.umc.domain.member.exception.MemberException;
import com.example.umc.domain.member.exception.code.MemberErrorCode;
import com.example.umc.domain.member.repository.MemberRepository;
import com.example.umc.global.security.entity.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService {
    // DB를 통해 기존 회원인지 확인하기 위한 서비스

    private final MemberRepository memberRepository;

    public UserDetails loadUserByUsername(
            // 회원정보 검색하는 녀석
            SocialType socialType,
            String username
    ) throws UsernameNotFoundException {
        Member member = memberRepository.findBySocialTypeAndSocialUid(socialType, username)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        return new AuthMember(member);
    }
}
