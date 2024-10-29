package com.joonhoe.blog.user.service;

import com.joonhoe.blog.auth.CustomOAuth2User;
import com.joonhoe.blog.auth.dto.OAuth2UserInfoDto;
import com.joonhoe.blog.user.entity.User;
import com.joonhoe.blog.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

  private final UserRepository userRepository;

  @Override
  @Transactional
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2User oauth2User = super.loadUser(userRequest);

    // OAuth2 사용자 정보를 DTO로 변환
    OAuth2UserInfoDto userInfo = extractUserInfo(userRequest, oauth2User);

    // 사용자 생성 또는 업데이트
    User user = processUserInfo(userInfo);

    return new CustomOAuth2User(
            oauth2User.getAuthorities(),
            oauth2User.getAttributes(),
            "email",
            user
    );
  }

  private OAuth2UserInfoDto extractUserInfo(OAuth2UserRequest userRequest, OAuth2User oauth2User) {
    String providerName = userRequest.getClientRegistration().getRegistrationId();

    return OAuth2UserInfoDto.builder()
            .providerId(oauth2User.getAttribute("sub"))
            .email(oauth2User.getAttribute("email"))
            .name(oauth2User.getAttribute("name"))
            .picture(oauth2User.getAttribute("picture"))
            .provider(User.Provider.valueOf(providerName.toUpperCase()))
            .build();
  }

  private User processUserInfo(OAuth2UserInfoDto userInfo) {
    return userRepository.findByProviderAndProviderId(
            userInfo.getProvider(),
            userInfo.getProviderId()
    ).map(existingUser -> {
      existingUser.updateUserInfo(
              userInfo.getEmail(),
              userInfo.getName(),
              userInfo.getPicture()
      );
      return existingUser;
    }).orElseGet(() -> userRepository.save(userInfo.toEntity()));
  }
}