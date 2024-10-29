package com.joonhoe.blog.post.repository;

import com.joonhoe.blog.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// RESTful API 엔드포인트 자동 생성
@RepositoryRestResource
public interface PostRepository extends JpaRepository<Post, Long> {
  // 상속으로 기본 쿼리메서드 존재
}
