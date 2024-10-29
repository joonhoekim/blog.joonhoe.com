package com.joonhoe.blog.auth;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j  // lombok annotation 추가
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // OAuth2User의 모든 attributes 출력
        log.debug("OAuth2User attributes: {}", oAuth2User.getAttributes());

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        log.debug("Extracted email: {}, name: {}", email, name);

        try {
            String token = jwtTokenProvider.createToken(email);
            log.debug("Generated JWT token: {}", token);

            String redirectUrl = String.format("http://localhost:3000/auth/callback?token=%s&email=%s&name=%s",
                    token, email, name);
            log.debug("Redirect URL: {}", redirectUrl);

            response.sendRedirect(redirectUrl);
        } catch (Exception e) {
            log.error("Error during OAuth2 success handling", e);
            response.sendRedirect("http://localhost:3000/login?error=auth");
        }
    }
}
