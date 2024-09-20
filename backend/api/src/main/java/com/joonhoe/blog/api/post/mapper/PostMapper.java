package com.joonhoe.blog.api.post.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapper {

  PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);
  // DTO to Entity 를 위한 Mapstruct Mapper 설정이다.
  // Post postDtoToPost(PostDto postDto);

}
