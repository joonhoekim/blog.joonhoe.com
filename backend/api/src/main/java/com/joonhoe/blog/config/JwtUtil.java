// src/main/java/com/joonhoe/blog/config/JwtUtil.java
package com.joonhoe.blog.config;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;

/**
 * JWT 토큰의 생성, 검증 및 클레임 추출을 처리하는 유틸리티 클래스
 */
@Log4j2
@Component
public class JwtUtil {

    /**
     * application.properties에서 JWT 비밀키를 가져옴
     */
    @Value("${jwt.secret}")
    private String secret;

    /**
     * application.properties에서 JWT 만료 시간을 가져옴 (밀리초 단위)
     */
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * JWT 서명에 사용될 키
     */
    private Key key;

    /**
     * 빈이 초기화될 때 JWT 서명용 키를 생성
     */
    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * UserDetails 객체로부터 JWT 토큰을 생성
     *
     * @param userDetails Spring Security의 UserDetails 객체
     * @return 생성된 JWT 토큰
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    /**
     * 실제 JWT 토큰을 생성하는 메서드
     *
     * @param claims 토큰에 포함될 클레임들
     * @param subject 토큰의 주체(일반적으로 username)
     * @return 생성된 JWT 토큰
     */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims) // 추가 클레임 설정
                .setSubject(subject) // 토큰 주체 설정
                .setIssuedAt(new Date(System.currentTimeMillis())) // 토큰 발행 시간
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // 토큰 만료 시간
                .signWith(key, SignatureAlgorithm.HS256) // 서명 알고리즘과 키 설정
                .compact();
    }

    /**
     * JWT 토큰의 유효성을 검증
     *
     * @param token 검증할 JWT 토큰
     * @return 토큰이 유효하면 true, 그렇지 않으면 false
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // JWT 관련 예외나 잘못된 인자가 전달된 경우 로깅
            log.error("Invalid JWT token: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 토큰에서 사용자명 추출
     *
     * @param token JWT 토큰
     * @return 토큰에서 추출한 사용자명
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * 토큰에서 특정 클레임을 추출
     *
     * @param token JWT 토큰
     * @param claimsResolver 클레임을 처리할 함수
     * @return 처리된 클레임 값
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * 토큰에서 모든 클레임을 추출
     *
     * @param token JWT 토큰
     * @return 토큰의 모든 클레임
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
