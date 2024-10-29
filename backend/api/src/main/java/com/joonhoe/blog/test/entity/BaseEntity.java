package com.joonhoe.blog.test.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) //아규먼트로 들어간 클래스가 자동으로 시간 값을 지정해준다. 이를 위해서 @EnableJpaAudting을 진입점에 추가해준다.
@Getter
abstract class BaseEntity {

  @CreatedDate
  @Column(name="reg_date", updatable = false)
  private LocalDateTime regDate;

  @LastModifiedDate
  @Column(name = "mod_date")
  private LocalDateTime modDate;


}
