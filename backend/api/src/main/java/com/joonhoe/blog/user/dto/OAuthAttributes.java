package com.joonhoe.blog.user.dto;

import com.joonhoe.blog.user.entity.User;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@Builder
@RequiredArgsConstructor
public class OAuthAttributes {
  private final Map<String, Object> attributes;
  private final String nameAttributeKey;
  private final String name;
  private final String email;
  private final String picture;
  private final User.Provider provider;
  private final String providerId;

  // OAuth2 응답을 DTO로 변환하는 정적 팩토리 메서드
  public static OAuthAttributes of(String registrationId,
      Map<String, Object> attributes) {
    if("google".equals(registrationId)) {
      return ofGoogle(attributes);
    }
    throw new IllegalArgumentException("Unsupported provider: " + registrationId);
  }

  private static OAuthAttributes ofGoogle(Map<String, Object> attributes) {
    return OAuthAttributes.builder()
        .name((String) attributes.get("name"))
        .email((String) attributes.get("email"))
        .picture((String) attributes.get("picture"))
        .provider(User.Provider.GOOGLE)
        .providerId((String) attributes.get("sub"))
        .attributes(attributes)
        .nameAttributeKey("sub")
        .build();
  }

  // DTO를 Entity로 변환
  public User toEntity() {
    return User.builder()
        .name(name)
        .email(email)
        .picture(picture)
        .provider(provider)
        .providerId(providerId)
        .build();
  }
}
