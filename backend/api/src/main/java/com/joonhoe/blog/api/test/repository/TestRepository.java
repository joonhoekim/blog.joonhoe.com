package com.joonhoe.blog.api.test.repository;

import com.joonhoe.blog.api.test.entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<TestEntity, Long> {


}
