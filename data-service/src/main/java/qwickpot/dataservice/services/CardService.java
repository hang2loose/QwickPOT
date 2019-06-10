package qwickpot.dataservice.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import qwickpot.dataservice.domain.Card;
import qwickpot.dataservice.repositories.CardRepository;
import qwickpot.dataservice.util.CsvObject;


@Service
public class CardService {

  private static final String COULD_NOT_FIND_MESSAGE = "Could not find: ";
  private CardRepository cardRepository;

  private ThemeService themeService;

  public CardService(CardRepository cardRepository, ThemeService themeService) {
    this.cardRepository = cardRepository;
    this.themeService = themeService;
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

  public void importCsvObject(CsvObject csvObject) {
    csvObject.getLineIterator()
        .forEachRemaining(line -> cardRepository.save(convertFromCsv(line)));
  }

  private Card convertFromCsv(List<String> csvLine) {
    UUID id = getUuid(csvLine.get(0));
    Card card = id == null ? new Card() : getCardFromRepo(id);

    card.setName(csvLine.get(1));
    card.setDescription(csvLine.get(2));
    card.setTheme(themeService.getThemeFromRepo(csvLine.get(3)));
    return card;
  }

  private UUID getUuid(String idString) {
    return idString == null ? null : UUID.fromString(idString);
  }
}
