package com.example.umc.global.config;

import com.example.umc.global.security.exception.CustomAccessDenied;
import com.example.umc.global.security.exception.CustomEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
            "/v3/api-docs/**",
            // 로그인
            "/auth/**"
    }; // 여기의 주소로 오는 거는 검사하지않고 그냥 통과. public Api가 여기에 포함

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Api 서버 만들때는 그냥 꺼두는구나.. 정도만
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(allowUris).permitAll()
                        .anyRequest().authenticated()
                ) // 정리하면 allowUrls나 여기엔 없지만 public api 인것들은 통과시키고 나머지는 전부 인증받게.
                .formLogin(form -> form
                        .defaultSuccessUrl("/swagger-ui/index.html", true)
                        .permitAll()
                ) // 폼 로그인에 대한 설정인데, 로그인 성공시에는 위에 주소로 리다이렉트, true이므로 로그인 성공시 항상.
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                ) // - `/logout` 경로로 로그아웃을 처리합니다. 로그아웃 성공 시 `/login?logout`으로 리다이렉트
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(customAccessDenied())
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