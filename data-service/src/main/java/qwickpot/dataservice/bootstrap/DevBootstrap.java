package qwickpot.dataservice.bootstrap;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import qwickpot.dataservice.domain.Card;
import qwickpot.dataservice.domain.Theme;
import qwickpot.dataservice.repositories.CardRepository;
import qwickpot.dataservice.repositories.ThemeRepository;
import qwickpot.dataservice.repositories.dummys.DummyCardRepository;
import qwickpot.dataservice.services.CardService;
import qwickpot.dataservice.services.ThemeService;
import qwickpot.dataservice.util.CSVReader;

@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

  private final DummyCardRepository dummyCardRepository;
  private final CardRepository cardRepository;
  private final ThemeRepository themeRepository;
  private final ThemeService themeService;
  private final CardService cardService;

  public DevBootstrap(
      DummyCardRepository dummyCardRepository,
      CardRepository cardRepository,
      ThemeRepository themeRepository, ThemeService themeService,
      CardService cardService) {
    this.dummyCardRepository = dummyCardRepository;
    this.cardRepository = cardRepository;
    this.themeRepository = themeRepository;
    this.themeService = themeService;
    this.cardService = cardService;
  }

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    if (dummyCardRepository.count() == 0L) {
      try {
        initDataFromCSV();
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    }
  }

  private void initDataFromCSV() throws FileNotFoundException {
    File themeFile = ResourceUtils.getFile("classpath:ThemeImport.csv");
    File cardFile = ResourceUtils.getFile("classpath:CardImport.csv");

    themeService.importCsvObject(CSVReader.getCsv(themeFile.getPath())
        .orElseThrow(() -> new IllegalArgumentException("Import Failure no CSV Found")));
    cardService.importCsvObject(CSVReader.getCsv(cardFile.getPath())
        .orElseThrow(() -> new IllegalArgumentException("Import Failure no CSV Found")));
  }

  private void initData() {
    // init Cards
    Card card1 = generateCard("card1", "YYYYYYY");
    Card card2 = generateCard("card2", "XXXXXXX");

    // inits Themes and save them
    Theme theme1 = generateTheme("theme1", card1);
    themeRepository.save(theme1);
    Theme theme2 = generateTheme("theme2", card2);
    themeRepository.save(theme2);

    // Persist changes to cards
    card1.setTheme(theme1);
    card2.setTheme(theme2);
    cardRepository.save(card1);
    cardRepository.save(card2);

    // adding 3rd Theme for testing of subthemes
    Theme theme3 = new Theme();
    theme3.setName("theme3");

    theme3.setParentTheme(theme1);
    theme2.setParentTheme(theme1);
    theme1.setSubThemes(Arrays.asList(theme2, theme3));
    themeRepository.save(theme1);
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

}

