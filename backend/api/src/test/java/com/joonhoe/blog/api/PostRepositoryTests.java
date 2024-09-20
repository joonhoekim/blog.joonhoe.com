package com.joonhoe.blog.api;

import com.joonhoe.blog.api.post.entity.Post;
import com.joonhoe.blog.api.post.repository.PostRepository;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PostRepositoryTests {

  @Autowired
  private PostRepository postRepository;

  @Test
  public void testInsertDummies() {
    IntStream.rangeClosed(1, 100).forEach(i -> {
      Post post = Post.builder().title(i + "th post").content(i + "th content").build();
      postRepository.save(post);
    });
  }

  @Test
  public void testFindAll() {
    List<Post> posts = postRepository.findAll();
    for (Post post : posts) {
      System.out.println(post);
    }
  }
}
