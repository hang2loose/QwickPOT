package qwickpot.dataservice.bootstrap;

import java.io.File;
import java.io.FileNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import qwickpot.dataservice.repositories.ThemeRepository;
import qwickpot.dataservice.services.CardService;
import qwickpot.dataservice.services.DeparmentService;
import qwickpot.dataservice.services.ThemeService;
import qwickpot.dataservice.util.CSVReader;

@Slf4j
@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

  private final ThemeRepository themeRepository;
  private final ThemeService themeService;
  private final CardService cardService;
  private final DeparmentService deparmentService;

  public DevBootstrap(
      ThemeRepository themeRepository, ThemeService themeService,
      CardService cardService, DeparmentService deparmentService) {
    this.themeRepository = themeRepository;
    this.themeService = themeService;
    this.cardService = cardService;
    this.deparmentService = deparmentService;
  }

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    log.info("starting import");
    if (themeRepository.count() == 0L) {
      try {
        addDepartments();
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

  private void addDepartments() {
    deparmentService.addNewDepartment("Projektmanagement");
    deparmentService.addNewDepartment("Operations/Planung");
    deparmentService.addNewDepartment("Strategie");
    deparmentService.addNewDepartment("Projektleiter-Pool");
  }
}