package com.joonhoe.blog.test.repository;

import com.joonhoe.blog.test.entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<TestEntity, Long> {


}
