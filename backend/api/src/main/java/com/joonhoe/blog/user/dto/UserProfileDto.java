// src/main/java/com/joonhoe/blog/user/dto/UserProfileDto.java

package com.joonhoe.blog.user.dto;

import com.joonhoe.blog.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileDto {
    private Long id;
    private String email;
    private String name;
    private String picture;
    private User.Provider provider;

    public static UserProfileDto from(User user) {
        return UserProfileDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .picture(user.getPicture())
                .provider(user.getProvider())
                .build();
    }
}