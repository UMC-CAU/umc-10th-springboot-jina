package com.example.umc.global.config;

import com.example.umc.global.security.exception.CustomAccessDenied;
import com.example.umc.global.security.exception.CustomEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final String[] allowUris = {
            // Swagger 허용
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/v3/api-docs/**"
    }; // Swagger 문서 확인용 경로만 공통 Public API로 열어둡니다.

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Api 서버 만들때는 그냥 꺼두는구나.. 정도만
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(allowUris).permitAll()
                        // 회원가입은 로그인 전에도 호출할 수 있어야 하므로 Public API로 허용합니다.
                        .requestMatchers(HttpMethod.POST, "/auth/signup").permitAll()
                        // 위에 적지 않은 모든 API는 Private API입니다. 즉, 로그인한 사용자만 접근할 수 있습니다.
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/swagger-ui/index.html", true)
                        .permitAll()
                ) // 폼 로그인에 대한 설정인데, 로그인 성공시에는 위에 주소로 리다이렉트, true이므로 로그인 성공시 항상.
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                ) // - `/logout` 경로로 로그아웃을 처리합니다. 로그아웃 성공 시 `/login?logout`으로 리다이렉트
                .exceptionHandling(exception -> exception
                        // 인가 실패: 로그인은 했지만 권한이 부족하면 403 공통 응답을 반환합니다.
                        .accessDeniedHandler(customAccessDenied())
                        // 인증 실패: 로그인하지 않은 사용자가 Private API에 접근하면 401 공통 응답을 반환합니다.
                        .authenticationEntryPoint(customEntryPoint())
                )
        ;
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    // 비밀번호 솔트를 위해 passwordEncoder 사용, PasswordEncoder 사용 방법은 SecurityConfig에서
    // PasswordEncoder에 대한 Bean 설정을 명시하고, 사용하는 계층에서 DI 받으면 됩니다!
    @Bean
    public CustomAccessDenied customAccessDenied(){
        return new CustomAccessDenied();
    }

    @Bean
    public CustomEntryPoint customEntryPoint() {
        return new CustomEntryPoint();
    }


}
