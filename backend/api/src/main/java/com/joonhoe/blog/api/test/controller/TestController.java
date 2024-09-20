package com.joonhoe.blog.api.test.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

  @GetMapping("/test")
  public String test() {
    return "Hello World";
  }

  @GetMapping("/testjson")
  public String[] testjson() {
    return new String[]{"Hello", "World"};
  }


}
