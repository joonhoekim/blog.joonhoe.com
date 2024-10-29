package com.joonhoe.blog.sample.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequestMapping(value="/sample/")
public class SampleController {

  @GetMapping("/all")
  public void exAll(){
    log.info("test: exAll");
  }

  @GetMapping("/admin")
  public void exMember(){
    log.info("test: exMember");
  }

  @GetMapping("/member")
  public void exAdmin(){
    log.info("test: exMember");
  }


}
