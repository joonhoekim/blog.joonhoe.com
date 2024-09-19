package com.joonhoe.blog.api;

import com.joonhoe.blog.api.test.entity.Memo;
import com.joonhoe.blog.api.test.entity.MemoJpaRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;

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
    if (result.isPresent()) {
      Memo memo = result.get();
      System.out.println(memo);
    }
  }

  @Transactional
  @Test
  public void testSelectByGetOne() {
    Long mno = 100L;
    // getOne()은 일단 프록시 객체 ID 만 가져오고, 나중에 참조할 때 DB 조회함
    // 이름이 불명확해서 deprecated 됨. getReferenceById 라는 메서드를 쓰길 바람.
    // LazyLoading 특성상 트랜잭션 범위 내에서 동작해야 하므로 @Transactional 필요
    Memo memo = memoJpaRepository.getOne(mno);
    System.out.println("-----이 이전에 get one 수행됨");
    System.out.println("-----아래 라인에서 get one 조회할 것임");
    System.out.println(memo);

    memo = memoJpaRepository.getReferenceById(mno - 1L);
    System.out.println("-----이 이전에 getReferenceById 수행됨");
    System.out.println("-----아래 라인에서 getReferenceById 조회할 것임");
    System.out.println(memo);
    // 테스트 실행해보면 getOne 수행된 이후, 참조 시점이 되어서야 hibernate 에서 쿼리가 실행됨을 볼 수 있음
    // 그리고 컨텍스트에 변화가 없다면, DB를 다시 조회하지 않음
  }

  @Test
  public void testUpdate() {
    Memo memo = Memo.builder().mno(100L).memoText("Updated Text").build();
    System.out.println(memoJpaRepository.save(memo));
    // update 라는 메서드는 없다. save를 쓰는데, 해당 ID가 없으면 INSERT, 있으면 UPDATE가 실행된다.
    // update에 대해 기억할 점은, JPA가 엔티티를 메모리상에 보관하도록 설정되어 있기에 select 구문이 먼저 실행된다는 것
  }

  @Test
  public void testDelete() {
    Long mno = 100L;
    memoJpaRepository.deleteById(mno);
    // delete 또한 select 를 먼저 수행하여 메모리상에 올린다.
  }

  // 페이징이나 정렬은 findAll()로 가능하며, Page 및 Pageable 타입을 쓸 수 있다.
  // 생성자가 protected 라서 new 키워드가 아닌 of 메서드로 인스턴스를 만든다.
  // org.springframework.data.domain 패키지에서 Page, Pageable, PageRequest를 사용한다.
  // 동일한 클래스를 갖는 패키지들이 여럿 있으니 주의.
  @Test
  public void testPageDefault() {
    //페이저블을 정의한다. 아래는 페이지의 원소 개수가 10개 일 때, 0번째 페이지로 페이저블을 정의한다.
    //페이저블을 정의할 때는 페이지리퀘스트를 사용한다.
    Pageable pageable = PageRequest.of(0, 10);
    //해당 페이저블의 정의대로 findAll 함수가 페이지를 만든다.
    Page<Memo> result = memoJpaRepository.findAll(pageable);

    System.out.println(result);
    // 페이지는 toString에서는 아래와 같이 나온다.
    // Page 1 of 10 containing com.joonhoe.blog.api.test.entity.Memo instances

    // 개별 원소를 조회하려면 내장 메서드들을 사용한다. 다양한 메서드들이 있다.

    // Stream으로 받으려면 get()
    result.get().forEach(System.out::println);

    // List으로 받으려면 getContent()
    for (Memo memo : result.getContent()) {
      System.out.println(memo);
    }

  }

  // 페이지를 정렬하는 방법
  @Test
  public void testSort() {
    // 소팅 방식을 정의한다.
    // 소팅은 해당 페이지에 대해서만 적용된다! 모든 데이터를 소팅하고 페이지를 만드는 게 아니다.
    Sort sort1 = Sort.by("mno").descending();

    //페이저블 만들 때 소팅 방법도 명시한다.
    Pageable pageable = PageRequest.of(0, 10, sort1);
    Page<Memo> result = memoJpaRepository.findAll(pageable);

    result.get().forEach(System.out::println);

    //정렬 조건을 여러개 쓸 수 있다. Comparator 와 유사하다. and() 메서드로 다음 조건을 묶는다.
    Sort sort2 = Sort.by("memoText").ascending();
    Sort sortAll = sort1.and(sort2);
    Pageable pageable2 = PageRequest.of(0, 10, sortAll);

    memoJpaRepository.findAll(pageable2).get().forEach(System.out::println);
  }

  //쿼리메서드 테스트
  @Test
  public void testQueryMethods() {
    List<Memo> list = memoJpaRepository.findByMnoBetweenOrderByMnoDesc(70L, 80L);
    list.forEach(System.out::println);

    //[50, 80] 구간의 ID 를 페이지를 나눠서 가져오기
    Pageable pageable = PageRequest.of(3, 10, Sort.by("mno").descending());
    Page<Memo> result = memoJpaRepository.findByMnoBetween(50L, 80L, pageable);
    result.get().forEach(System.out::println);
    // 위 코드는 여러 페이지를 가져온다. 미리 여러 페이지를 로딩하려는 경우 쓸 수 있다.
    // Page 클래스의 hasNext() 메서드로 다음 페이지가 있는지 체크할 수 있다.
  }

  @Test
  @Transactional // remove 콜은 트랜잭션으로 처리되어야 한다.
  @Commit // 커밋하지 않으면 rollback 된다.
  public void testDeleteQueryMethods() {
    memoJpaRepository.deleteMemoByMnoLessThan(10L);
  }
  // 이러한 deleteBy 는 실제 개발에는 거의 사용되지 않는다.
  // 하나씩 개별 삭제하기에 비효율적이기 때문이다. @Query 혹은 QueryDSL 을 사용한다.
  // 자세한 내용은 memoJpaRepository 에 공부하며 적어두었다.
  
}
