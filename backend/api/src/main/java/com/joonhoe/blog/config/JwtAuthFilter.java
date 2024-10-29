// src/main/java/com/joonhoe/blog/config/JwtAuthFilter.java

package com.joonhoe.blog.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT 토큰을 검증하고 인증 정보를 설정하는 필터
 * Spring Security 필터 체인에서 UsernamePasswordAuthenticationFilter 이전에 동작
 */
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    /**
     * 모든 HTTP 요청에 대해 JWT 토큰을 검증하고 인증 정보를 설정
     *
     * @param request HTTP 요청
     * @param response HTTP 응답
     * @param filterChain 필터 체인
     * @throws ServletException 서블릿 예외
     * @throws IOException I/O 예외
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // Authorization 헤더에서 JWT 토큰 추출
        final String authHeader = request.getHeader("Authorization");

        // Bearer 토큰이 아닌 경우 다음 필터로 진행
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // "Bearer " 이후의 실제 토큰 값 추출
            final String jwt = authHeader.substring(7);
            // 토큰에서 사용자명 추출
            final String username = jwtUtil.extractUsername(jwt);

            // 사용자명이 존재하고 현재 SecurityContext에 인증 정보가 없는 경우
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // 토큰이 유효한 경우
                if (jwtUtil.validateToken(jwt)) {
                    // UserDetails 객체 로드
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                    // 인증 토큰 생성
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,          // 인증된 사용자 정보
                            null,                 // credentials (보안상 비워둠)
                            userDetails.getAuthorities()  // 사용자 권한 목록
                    );

                    // SecurityContext에 인증 정보 설정
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            // JWT 처리 중 발생한 모든 예외를 로깅
            logger.error("JWT Authentication failed: " + e.getMessage());
        }

        // 다음 필터로 진행
        filterChain.doFilter(request, response);
    }
}