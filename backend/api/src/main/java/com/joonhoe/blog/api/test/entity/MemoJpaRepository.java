package com.joonhoe.blog.api.test.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoJpaRepository extends JpaRepository<Memo, Long> {

}
