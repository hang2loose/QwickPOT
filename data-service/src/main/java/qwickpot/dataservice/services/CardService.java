package qwickpot.dataservice.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import qwickpot.dataservice.domain.Card;
import qwickpot.dataservice.exceptions.CardNotFoundException;
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
        .orElseThrow(
            () -> new CardNotFoundException(COULD_NOT_FIND_MESSAGE + "card: " + name));
  }

  public Card getCardFromRepo(Long id) {
    Optional<Card> card = cardRepository.findById(id);
    return card
        .orElseThrow(
            () -> new CardNotFoundException(
                COULD_NOT_FIND_MESSAGE + "card with id : " + id.toString()));
  }

  public void importCsvObject(CsvObject csvObject) {
    csvObject.getLineIterator()
        .forEachRemaining(line -> cardRepository.save(convertFromCsv(line)));
  }

  private Card convertFromCsv(List<String> csvLine) {
    Long id = Long.getLong(csvLine.get(0));
    Card card = id == null ? new Card() : getCardFromRepo(id);

    card.setName(csvLine.get(1));
    card.setDescription(csvLine.get(2));
    card.setTheme(themeService.getThemeFromRepo(csvLine.get(3)));
    return card;
  }
}
