package com.joonhoe.blog.api;

import com.joonhoe.blog.api.test.entity.TestEntity;
import com.joonhoe.blog.api.test.repository.TestRepository;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@Slf4j
public class TestRepositoryTest {

  @Autowired
  private TestRepository testRepository;

  @Test
  public void testInsert() {
    IntStream.rangeClosed(1,100).forEach(i->{
      TestEntity testEntity = TestEntity.builder
    })
  }

}