package com.joonhoe.blog.api;

import com.joonhoe.blog.api.test.entity.Memo;
import com.joonhoe.blog.api.test.entity.MemoJpaRepository;
import java.util.Optional;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
public class MemoRepositoryTests {

  @Autowired
  MemoJpaRepository memoJpaRepository;

  @Test
  public void testClass() {
    System.out.println("USER LOG: " + memoJpaRepository.getClass().getName());
  }

  @Test
  public void testInsertDummies() {
    IntStream.rangeClosed(1, 100).forEach(i -> {
      Memo memo = Memo.builder().memoText("Sample..." + i).build();
      memoJpaRepository.save(memo);
    });
  }

  @Test
  public void testSelect() {
    Long mno = 100L;
    Optional<Memo> result = memoJpaRepository.findById(mno);
    System.out.println("-------------------");
    if(result.isPresent()){
      Memo memo = result.get();
      System.out.println(memo);
    }
  }

}
