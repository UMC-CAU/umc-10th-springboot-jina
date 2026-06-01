package com.example.umc.global.security.entity;

import com.example.umc.domain.member.entity.Member;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class AuthMember implements UserDetails {
    // JWT 토큰 형식으로 생기는 인증 객체
    // AuthMember는 Member를 스프링이 읽을 수 있게 해주는 녀석
    private final Member member;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public @Nullable String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return member.getSocialUid();
    }
}