package com.joonhoe.blog.api.post.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostDto {

  private Long id;
  private String title;
  private String content;
}
