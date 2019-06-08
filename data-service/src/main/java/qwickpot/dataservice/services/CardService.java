package qwickpot.dataservice.services;

import org.springframework.stereotype.Service;
import qwickpot.dataservice.domain.Card;
import qwickpot.dataservice.dtos.CardDto;
import qwickpot.dataservice.repositories.CardRepository;


@Service
public class CardService {

  private CardRepository cardRepository;

  public CardService(CardRepository cardRepository) {
    this.cardRepository = cardRepository;
  }

  public CardDto getCardFromRepoByName(String name) {
    Card card = cardRepository.getCardByName(name);

    return new CardDto(card);
  }
}
