package qwickpot.dataservice.controllers;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardController {

  @RequestMapping("/dummy")
  public String dummyEndPoint() {
    return "Hello World";
  }
}
