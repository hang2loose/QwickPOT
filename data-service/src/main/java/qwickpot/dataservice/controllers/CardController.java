package qwickpot.dataservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import qwickpot.dataservice.dtos.CardDto;
import qwickpot.dataservice.services.CardService;
import qwickpot.dataservice.services.StatService;

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
  public CardDto getCardByName(
      @RequestParam(name = "ID") Long id) {
    return CardDto.convertToDtoFromEntity(cardService.getCardFromRepo(id));
  }

  @GetMapping("/getCardByIdStat")
  public CardDto getCardByNameAddCallToStat(
      @RequestParam(name = "ID") Long id,
      @RequestParam(name = "DepartmentId") Long departmentId) {
    statService.addStat(departmentId, id);
    return CardDto.convertToDtoFromEntity(cardService.getCardFromRepo(id));
  }
}
