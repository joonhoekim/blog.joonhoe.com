package com.joonhoe.blog.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
// @ToString // 순환참조 등 성능 문제 발생 가능성으로 제외
@Getter
@Builder
@AllArgsConstructor // For Builder
@NoArgsConstructor // For JPA
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    private String picture;

    // OAuth 제공자 구분
    @Enumerated(EnumType.STRING)
    private Provider provider;

    // OAuth Provider 가 제공하는 ID
    private String providerId;

    // 활성 유저 여부, 기본값 true
    @Builder.Default
    @Column(nullable = true)
    private boolean isActive = true;

    // 활성 유저 여부, 기본값 true
    @Builder.Default
    @Column(nullable = false)
    private boolean isDeleted = true;

    // 별도의 클래스로 빼지 말자. (Information Expert)
    public enum Provider {
        GOOGLE,
    }

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.USER;

    // 업데이트 메서드 추가
    public void updateUserInfo(String email, String name, String picture) {
        this.email = email;
        this.name = name;
        this.picture = picture;
    }

    public enum Role {
        USER, ADMIN
    }
}
