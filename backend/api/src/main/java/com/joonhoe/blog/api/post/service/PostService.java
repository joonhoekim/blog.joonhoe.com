package com.joonhoe.blog.api.post.service;

import com.joonhoe.blog.api.post.dto.PostDto;
import com.joonhoe.blog.api.post.entity.Post;
import com.joonhoe.blog.api.post.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class PostService {

  private final PostRepository postRepository;

  public PostService(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  public Post createdPost(PostDto postDto) {
    Post post = Post.builder().title(postDto.getTitle()).content(postDto.getContent()).build();
    return postRepository.save(post);
  }

  public Post getPostById(Long id) {
    return postRepository.findById(id).orElse(null);
  }

}
