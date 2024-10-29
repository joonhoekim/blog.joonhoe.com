// src/main/java/com/joonhoe/blog/user/service/UserService.java
package com.joonhoe.blog.user.service;

import org.springframework.stereotype.Service;

import com.joonhoe.blog.user.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    // 기본 CRUD 서비스는 spring data rest 가 만든다.

    @Transactional
    public void withdrawUser(Long userId) {
        userRepository.findById(userId).ifPresent(userRepository::delete);
    }
}
