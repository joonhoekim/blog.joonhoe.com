// src/main/java/com/joonhoe/blog/config/SecurityConfig.java
package com.joonhoe.blog.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.joonhoe.blog.auth.OAuth2SuccessHandler;
import com.joonhoe.blog.user.service.OAuth2UserService;

import lombok.RequiredArgsConstructor;

/**
 * Spring Security 설정 클래스 API 서버를 위한 보안 설정을 정의합니다.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final OAuth2UserService oAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    /**
     * 인증 없이 접근 가능한 URL 패턴 목록
     */
    private static final String[] PUBLIC_URLS = {
        "/",
        "/auth/**", // 인증 관련 엔드포인트
        "/api/public/**", // 공개 API 엔드포인트
        "/api/v1/public/**", // 버전별 공개 API
        "/health", // 헬스체크
        "/actuator/**", // 모니터링
        // Swagger UI
        "/swagger-ui/**",
        "/v3/api-docs/**", // OpenAPI 문서
        "/swagger-resources/**",
        "/webjars/**",
        // 공용 페이지
        "/error" // 에러 페이지
    };

    /**
     * Spring Security 필터 체인 설정 보안 관련 주요 설정을 정의합니다.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, CorsConfigurationSource corsConfigurationSource) throws Exception {
        http
                // API 서버 설정
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS 설정 적용
                .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화 (API 서버이므로)
                .sessionManagement(session
                        -> // 세션 관리 설정
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // JWT 사용을 위한 세션리스 설정

                // URL 기반 보안 설정
                .authorizeHttpRequests(auth
                        -> auth
                        .requestMatchers(PUBLIC_URLS).permitAll() // 공개 URL 설정
                        .anyRequest().authenticated() // 그 외 모든 요청은 인증 필요
                )
                // OAuth2 로그인 설정
                .oauth2Login(oauth2Config
                        -> oauth2Config
                        .userInfoEndpoint(userInfo
                                -> // OAuth2 사용자 정보 엔드포인트 설정
                                userInfo.userService(oAuth2UserService)
                        )
                        .successHandler(oAuth2SuccessHandler) // 로그인 성공 시 처리자
                )
                // 예외 처리 설정
                .exceptionHandling(e -> e
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)) // 인증 실패 시 401 응답
                )
                // JWT 필터 추가
                .addFilterBefore(new JwtAuthFilter(jwtUtil, userDetailsService), // JWT 인증 필터 추가
                        UsernamePasswordAuthenticationFilter.class) // UsernamePassword 필터 이전에 실행

                // 로그아웃 설정
                .logout(logout -> logout
                .logoutSuccessUrl("/") // 로그아웃 성공 시 리다이렉트 URL
                .clearAuthentication(true) // 인증 정보 제거
                .deleteCookies("JSESSIONID") // 세션 쿠키 삭제
                );

        return http.build();
    }

    /**
     * CORS 설정 Cross-Origin Resource Sharing 정책을 정의합니다.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // 허용할 오리진 설정 (프론트엔드 서버 주소)
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        // 허용할 HTTP 메서드 설정
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // 허용할 헤더 설정
        configuration.setAllowedHeaders(Arrays.asList("*"));
        // 인증 정보 포함 허용
        configuration.setAllowCredentials(true);
        // preflight 요청 캐시 시간 (초)
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // /api/** 경로에 대해 CORS 설정 적용
        source.registerCorsConfiguration("/api/**", configuration);

        return source;
    }
}
