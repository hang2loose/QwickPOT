package qwickpot.dataservice.services;

import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import qwickpot.dataservice.domain.Card;
import qwickpot.dataservice.repositories.CardRepository;


@Service
public class CardService {

  public static final String COULD_NOT_FIND_MESSAGE = "Could not find: ";
  private CardRepository cardRepository;

  public CardService(CardRepository cardRepository) {
    this.cardRepository = cardRepository;
  }

  public Card getCardFromRepo(String name) {
    Optional<Card> card = cardRepository.getCardByName(name);
    return card
        .orElseThrow(() -> new IllegalArgumentException(COULD_NOT_FIND_MESSAGE + name));
  }

  public Card getCardFromRepo(UUID uuid) {
    Optional<Card> card = cardRepository.findById(uuid);
    return card
        .orElseThrow(() -> new IllegalArgumentException(COULD_NOT_FIND_MESSAGE + uuid.toString()));
  }

}
