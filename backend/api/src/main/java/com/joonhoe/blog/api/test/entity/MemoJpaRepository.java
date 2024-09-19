package com.joonhoe.blog.api.test.entity;

import jakarta.transaction.Transactional;
import java.util.List;
import jdk.jfr.Percentage;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemoJpaRepository extends JpaRepository<Memo, Long> {

  // Query Method
  List<Memo> findByMnoBetweenOrderByMnoDesc(Long from, Long to);

  // 위의 쿼리메서드처럼, 메서드 이름이 너무 길어지는 경우 Pageable 파라미터를 결합해 사용하도록 선언할 것
  Page<Memo> findByMnoBetween(Long from, Long to, Pageable pageable);

  // 여러가지 쿼리를 쿼리메서드로 만들 수 있다.
  // id 기준으로 지운다거나 (이미 있지만)
  void deleteMemoByMno(Long mno);

  // 지원하는 기준들로 지울 수 있다. 자세한 건 doc 참조
  void deleteMemoByMnoLessThan(Long mno);

  // Query 보다는 QueryDSL 을 사용하는 게 트렌드다. 왜?
  // - Query 는 JPQL 을 문자열로 작성해 value로 넘기다보니 컴파일 이전 쿼리 문법 오류를 잡기 어렵다.
  // - QueryDSL 은 쿼리 로직을 메서드로 분리해 재사용할 수 있다.
  // - 1, 2의 이유로 QueryDSL 을 테스트하는 것이 더 쉽다.
  // 그래도 @Query 가 무엇인지는 알아보자.

  // @Query(value = ) 에서 value 는 JPQL 문자열을 입력한다.
  // JPQL은 SQL과 매우 유사하다. 단 DB의 테이블/컬럼명을 쓰지 않고, Entity 클래스의 멤버변수들을 쓰기에 편리하다.
  // @Query 에서 파라미터 바인딩하는 방법은 여러 가지가 있다.
  // 1. 파라미터의 순서를 이용하는 방식 (?1, ?2 처럼 작성하고 1부터 시작함)
  // 2. 파라미터의 이름을 이용하는 방식 (:name)
  // 3. java bean style (#{ })
  // 아래 예시들을 보면, Mybatis 쓸 때보다는 낫지만, 이 또한 쉽지 않아보인다.
  @Query("select m from Memo m order by m.mno desc ")
  List<Memo> getListDesc();

  @Transactional
  @Modifying
  @Query("update Memo m set m.memoText = :memoText where m.mno = :mno")
  int updateMemoText(@Param("mno") Long mno, @Param("memoText") String memoText);

  @Transactional
  @Modifying
  @Query("update Memo m set m.memoText = :#{#param.memoText} where m.mno = #{#param.mno}")
  int updateMemoText(@Param("param") Memo memo);

  @Query(value = "select m from Memo m where m.mno > :mno", countQuery = "select count(m) from Memo m where m.mno > :mno")
  Page<Memo> getListWithQuery(Long mno, Pageable pageable);

  // @Query 를 이용하는 경우, 쿼리메서드와 달리 엔터티 타입이 아닌 데이터도 추출이 가능하다. 필요한 데이터만 추출이 가능하다는 말.
  // Object[] 를 리턴타입으로 지정할 수 있다. 예를 들어 아래와 같은 활용이 가능하다.
  @Query(value = "select m.mno, m.memoText, CURRENT_DATE from Memo m where m.mno > :mno", countQuery = "select count(m) from Memo m where m.mno > :mno")
  Page<Object[]> getListWithQueryObject(Long mno, Pageable pageable);

  // native SQL 을 직접 사용할 수도 있다. ORM 의 장점을 잃게 되지만, 아주 복잡한 구문 처리 혹은 특정 DB의 파인 튜닝을 목적으로 사용한다.
  @Query(value = "select * from ememo where mno > 0", nativeQuery = true)
  List<Object> getNativeResult();

}
