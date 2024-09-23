package com.joonhoe.blog.api.user.repository;

import com.joonhoe.blog.api.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//RESTful API 엔드포인트 자동 생성
@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long> {
  //JpaRepository 상속함으로, 기본 쿼리메서드를 상속함.
}
