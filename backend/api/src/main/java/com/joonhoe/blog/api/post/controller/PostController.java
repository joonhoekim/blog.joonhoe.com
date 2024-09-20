package com.joonhoe.blog.api.post.controller;

import com.joonhoe.blog.api.post.dto.PostDto;
import com.joonhoe.blog.api.post.entity.Post;
import com.joonhoe.blog.api.post.service.PostService;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class PostController {

  private final PostService postService;

  //@Autowired //생성자 하나만 있는 경우는 @Autowired 필요 없음
  public PostController(PostService postService) {
    this.postService = postService;
  }

  @GetMapping("{id}")
  public ResponseEntity<Post> getPost(@PathVariable Long id) {
    return Optional.ofNullable(postService.getPostById(id)).map(post -> {
      // 조회수 증가, 로깅 등 처리 가능!
      return ResponseEntity.ok(post);
    }).orElseGet(() -> {
      return ResponseEntity.notFound().build();
    });
  }

  @PostMapping("/post")
  public ResponseEntity<Void> createPost(@RequestBody PostDto postDto) {
    postService.createdPost(postDto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }


}
