package com.example.umc.global.security.service;

import com.example.umc.domain.member.entity.Member;
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
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(
            // 회원정보 검색하는 녀석
            String username
    ) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        return new AuthMember(member);
    }
}