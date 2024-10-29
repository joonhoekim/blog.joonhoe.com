package com.joonhoe.blog.auth.dto;

import com.joonhoe.blog.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OAuth2UserInfoDto {
    private final String providerId;
    private final String email;
    private final String name;
    private final String picture;
    private final User.Provider provider;

    public User toEntity() {
        return User.builder()
                .email(email)
                .name(name)
                .picture(picture)
                .provider(provider)
                .providerId(providerId)
                .build();
    }
}