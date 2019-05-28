package qwickpot.dataservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardController {

  @GetMapping("/dummy")
  public String dummyEndPoint() {
    return "Hello World";
  }
}
