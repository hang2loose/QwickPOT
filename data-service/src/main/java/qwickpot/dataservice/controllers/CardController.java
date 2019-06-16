package qwickpot.dataservice.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import qwickpot.dataservice.dtos.CardDto;
import qwickpot.dataservice.services.CardService;
import qwickpot.dataservice.services.StatService;

@Slf4j
@RestController
public class CardController {

  private CardService cardService;
  private StatService statService;

  public CardController(CardService cardService, StatService statService) {
    this.cardService = cardService;
    this.statService = statService;
  }

  @GetMapping("/getCardByName")
  public CardDto getCardByName(
      @RequestParam(name = "CardName") String name) {
    return CardDto.convertToDtoFromEntity(cardService.getCardFromRepo(name));
  }

  @GetMapping("/getCardById")
  public CardDto getCardById(
      @RequestParam(name = "CardId") Long cardId,
      @RequestParam(name = "DepartmentId", required = false) Long departmentId
  ) {
    if (departmentId != null) {
      log.info("incrementing Stat for ThemeID: {} in DepartmentID {}", cardId, departmentId);
      statService.addStatWithFromCard(departmentId, cardId);
    }
    return CardDto.convertToDtoFromEntity(cardService.getCardFromRepo(cardId));
  }
}
