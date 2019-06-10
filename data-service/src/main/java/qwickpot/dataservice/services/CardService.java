package qwickpot.dataservice.services;

import java.util.Optional;
import java.util.UUID;
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

  public CardDto getCardFromRepo(String name) {
    Optional<Card> card = cardRepository.getCardByName(name);
    return card.map(CardDto::convertToDtoFromEntity).orElse(null);
  }

  public CardDto getCardFromRepo(UUID uuid) {
    Optional<Card> card = cardRepository.findById(uuid);
    return card.map(CardDto::convertToDtoFromEntity).orElse(null);
  }
}
