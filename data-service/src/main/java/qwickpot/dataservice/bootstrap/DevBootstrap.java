package qwickpot.dataservice.bootstrap;

import java.io.File;
import java.io.FileNotFoundException;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import qwickpot.dataservice.repositories.ThemeRepository;
import qwickpot.dataservice.services.CardService;
import qwickpot.dataservice.services.ThemeService;
import qwickpot.dataservice.util.CSVReader;

@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

  private final ThemeRepository themeRepository;
  private final ThemeService themeService;
  private final CardService cardService;

  public DevBootstrap(
      ThemeRepository themeRepository, ThemeService themeService,
      CardService cardService) {
    this.themeRepository = themeRepository;
    this.themeService = themeService;
    this.cardService = cardService;
  }

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    if (themeRepository.count() == 0L) {
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

}