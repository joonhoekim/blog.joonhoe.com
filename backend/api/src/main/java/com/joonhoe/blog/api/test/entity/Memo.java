package com.joonhoe.blog.api.test.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.boot.autoconfigure.web.WebProperties.Resources.Chain.Strategy;
import org.springframework.data.jpa.repository.JpaRepository;

@Entity
@Table(name="tbl_memo")
@ToString
@Getter
@Builder
//Builder REQUIRES AllArgsConstructor and NoArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class Memo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long mno;

  @Column(length=200, nullable = false)
  private String memoText;

}
