package com.example.umc.global.config;

import com.example.umc.global.security.exception.CustomAccessDenied;
import com.example.umc.global.security.exception.CustomEntryPoint;
import com.example.umc.global.security.filter.JwtAuthFilter;
import com.example.umc.global.security.handler.OAuthSuccessHandler;
import com.example.umc.global.security.service.CustomOAuthService;
import com.example.umc.global.security.service.CustomUserDetailsService;
import com.example.umc.global.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final CustomOAuthService customOAuthService;

    private final String[] allowUris = {
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/v3/api-docs/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)

                // 1. URI 출입 규칙 설정
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(allowUris).permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/signup").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        // OAuth 로그인 시작 주소와 카카오 콜백 주소는 로그인 전에도 접근 가능해야 합니다.
                        .requestMatchers("/oauth/authorize/**", "/oauth/callback/**").permitAll()
                        .anyRequest().authenticated()
                )

                // 2. JWT 쓸 거니까 폼 로그인은 비활성화!
                .formLogin(AbstractHttpConfigurer::disable)

                // 3. JWT 요청은 세션을 새로 만들지 않되, OAuth 로그인 과정에서 필요한 세션은 사용할 수 있게 둡니다.
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )

                // 4. 기존 검사대 앞에 내가 만든 JWT 토큰 검사원 배치!
                .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class)

                // 5. 로그아웃 설정
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                //OAuth
                .oauth2Login(oauth -> oauth
                        // // 인증 엔트리 포인트 (카카오 로그인 시작 주소 커스텀)
                        .authorizationEndpoint(auth -> auth
                                .baseUri("/oauth/authorize")
                        )
                        // // 콜백 주소 (카카오가 인증 코드를 보내줄 주소 커스텀)
                        .redirectionEndpoint(redirect -> redirect
                                .baseUri("/oauth/callback/*")
                        )
                        // // 인증 완료 후 정보 활용 (카카오 유저 정보 가공할 서비스 등록)
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuthService)
                        )
                        // // 성공 시 JWT 토큰 발행할 핸들러 등록
                        .successHandler(oAuthSuccessHandler())
                )

                // 6. 에러 상황 핸들러 등록
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(customAccessDenied())
                        .authenticationEntryPoint(customEntryPoint())

                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CustomAccessDenied customAccessDenied(){
        return new CustomAccessDenied();
    }

    @Bean
    public CustomEntryPoint customEntryPoint() {
        return new CustomEntryPoint();
    }

    @Bean
    public JwtAuthFilter jwtAuthFilter(){
        return new JwtAuthFilter(jwtUtil, customUserDetailsService);
    }

    @Bean
    public OAuthSuccessHandler oAuthSuccessHandler() {
        return new OAuthSuccessHandler(jwtUtil);
    }

}
