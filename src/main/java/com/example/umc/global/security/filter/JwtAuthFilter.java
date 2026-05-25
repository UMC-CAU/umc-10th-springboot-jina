package com.example.umc.global.security.filter;

import com.example.umc.global.apiPayload.ApiResponse;
import com.example.umc.global.apiPayload.code.BaseErrorCode;
import com.example.umc.global.apiPayload.code.GeneralErrorCode;
import com.example.umc.domain.member.enums.SocialType;
import com.example.umc.global.security.service.CustomUserDetailsService;
import com.example.umc.global.security.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter { // 토큰 검사원, JWT 토큰이 들어왔을때 인증 객체를 생성하는 필터

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // 토큰 가져오기
        String token = request.getHeader("Authorization");
        // token이 없거나 Bearer가 아니면 JWT 필터는 아무것도 하지 않고 다음 필터로 넘깁니다.
        // OAuth 콜백처럼 JWT가 없는 정상 요청까지 여기서 401로 덮어쓰지 않기 위해 try-catch 밖에서 처리합니다.
        if (token == null || !token.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // Bearer이면 추출
            token = token.replace("Bearer ", "");
            // AccessToken 검증하기: 올바른 토큰이면
            if (jwtUtil.isValid(token)) {
                // JWT 토근에서 유저정보 조회:Uid와 소셜로그인 타입 가져오기
                String uid = jwtUtil.getUid(token);
                SocialType socialType = jwtUtil.getSocialType(token);
                // 인증 객체 생성: 토큰에서 꺼낸 소셜 타입과 uid로 회원을 찾아 인증 객체 생성
                UserDetails member = customUserDetailsService.loadUserByUsername(socialType, uid);
                Authentication auth = new UsernamePasswordAuthenticationToken(
                        member,
                        null,
                        member.getAuthorities()
                );
                // 인증 완료 후 SecurityContextHolder에 넣기
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (Exception e) {
            ObjectMapper mapper = new ObjectMapper();
            BaseErrorCode code = GeneralErrorCode.UNAUTHORIZED;

            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(code.getStatus().value());

            ApiResponse<Void> errorResponse = ApiResponse.onFailure(code,null);

            mapper.writeValue(response.getOutputStream(), errorResponse);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
