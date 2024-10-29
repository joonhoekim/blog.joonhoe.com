// src/main/java/com/joonhoe/blog/user/controller/UserController.java

package com.joonhoe.blog.user.controller;

import com.joonhoe.blog.user.dto.UserProfileDto;
import com.joonhoe.blog.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.joonhoe.blog.auth.CustomOAuth2User;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserProfileDto> getCurrentUser(
            @AuthenticationPrincipal CustomOAuth2User currentUser) {
        return ResponseEntity.ok(
                UserProfileDto.from(currentUser.getUser())
        );
    }

    @DeleteMapping("/me")
    public ResponseEntity<?> withdrawUser(
            @AuthenticationPrincipal CustomOAuth2User currentUser) {
        userService.withdrawUser(currentUser.getUser().getId());
        return ResponseEntity.ok().build();
    }
}