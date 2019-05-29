package qwickpot.dataservice.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import qwickpot.dataservice.services.JokesService;

@Slf4j
@RestController
public class DummyController {

  private JokesService jokeSercive;

  @Autowired
  public DummyController(JokesService jokeSercive) {
    this.jokeSercive = jokeSercive;
  }

  @GetMapping("/dummy")
  public String dummyEndPoint() {
    return "Hello World";
  }

  @GetMapping("/chucky")
  public String chuckyQuote() {
    return jokeSercive.getJoke();
  }
}
