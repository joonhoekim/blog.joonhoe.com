package com.joonhoe.blog.user.repository;

import com.joonhoe.blog.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// RESTful API 엔드포인트 자동 생성한다. (엔티티 이름 복수형으로 엔드포인트 생성되고, 커스텀하고 싶으면 path 값을 주면 된다.)
@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long> {
  // JpaRepository 상속함으로, 기본 쿼리메서드를 상속한다.

  // OAuth 서비스는 자동생성 메서드 사용 X (REST API와 다른 로직이 요구되기 때문에)
  Optional<User> findByEmail(String email);
  Optional<User> findByProviderAndProviderId(User.Provider provider, String providerId);
}