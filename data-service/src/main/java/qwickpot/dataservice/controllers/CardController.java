package qwickpot.dataservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import qwickpot.dataservice.dtos.CardDto;
import qwickpot.dataservice.services.CardService;

@RestController
public class CardController {

  private CardService cardService;

  public CardController(CardService cardService) {
    this.cardService = cardService;
  }

  @GetMapping("/getCardByName")
  public CardDto getCardByName(
      @RequestParam(name = "CardName") String name) {

    return cardService.getCardFromRepoByName(name);
  }
}
