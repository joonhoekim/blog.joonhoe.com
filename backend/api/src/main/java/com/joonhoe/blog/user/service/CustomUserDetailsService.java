// src/main/java/com/joonhoe/blog/user/service/CustomUserDetailsService.java
package com.joonhoe.blog.user.service;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joonhoe.blog.user.entity.User;
import com.joonhoe.blog.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
    }

    private UserDetails createUserDetails(User user) {
        // OAuth 사용자는 기본적으로 USER 권한을 가짐
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password("") // OAuth 사용자는 비밀번호가 없음
                .authorities(Collections.singleton(authority))
                .accountExpired(false)
                .accountLocked(user.isDeleted()) // isDeleted가 true면 계정 잠금
                .credentialsExpired(false)
                .disabled(!user.isActive()) // isActive가 false면 계정 비활성화
                .build();
    }
}
