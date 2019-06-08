package qwickpot.dataservice.bootstrap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import qwickpot.dataservice.domain.Card;
import qwickpot.dataservice.domain.Theme;
import qwickpot.dataservice.domain.dummys.DummyCard;
import qwickpot.dataservice.repositories.CardRepository;
import qwickpot.dataservice.repositories.ThemeRepository;
import qwickpot.dataservice.repositories.dummys.DummyCardRepository;

@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

  private final DummyCardRepository dummyCardRepository;
  private final CardRepository cardRepository;
  private final ThemeRepository themeRepository;

  public DevBootstrap(DummyCardRepository dummyCardRepository,
      CardRepository cardRepository, ThemeRepository themeRepository) {
    this.dummyCardRepository = dummyCardRepository;
    this.cardRepository = cardRepository;
    this.themeRepository = themeRepository;
  }

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    if (dummyCardRepository.count() == 0L) {
      initData();
    }
  }

  private void initDummyCards() {

    dummyCardRepository.save(new DummyCard("dummy", "1st dummy DummyCard"));
    dummyCardRepository.save(new DummyCard("tes", "1st Test DummyCard"));
    dummyCardRepository.save(new DummyCard("dummy", "2st dummy DummyCard"));
    dummyCardRepository.save(new DummyCard("bubu", "ProjectsLead"));
    dummyCardRepository.save(new DummyCard("Jules", "Atanua4Life"));
  }

  private void initData() {
    Card card1 = generateCard("card1", "YYYYYYY");
    Card card2 = generateCard("card2", "XXXXXXX");

    Theme theme1 = generateTheme("theme1", card1);
    themeRepository.save(theme1);
    Theme theme2 = generateTheme("theme2", card2);
    themeRepository.save(theme2);
    card1.setTheme(theme1);
    card2.setTheme(theme2);
    cardRepository.save(card1);
    cardRepository.save(card2);
  }

  private Theme generateTheme(String name, Card... cards) {
    Theme tempTheme = new Theme();
    tempTheme.setName(name);
    tempTheme.setCards(Arrays.asList(cards));

    return tempTheme;
  }

  private Card generateCard(String name, String description) {
    Card tmpCard = new Card();

    tmpCard.setName(name);
    tmpCard.setDescription(description);

    return tmpCard;
  }

  private List<Card> genereateCards(String name, String description) {
    List<Card> tempList = new ArrayList<>();

    Card tempCard = new Card();
    tempCard.setName(name);
    tempCard.setDescription(description);

    tempList.add(tempCard);
    return tempList;
  }
}

