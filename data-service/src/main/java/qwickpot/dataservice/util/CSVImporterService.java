package qwickpot.dataservice.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import qwickpot.dataservice.domain.dummys.DummyCard;
import qwickpot.dataservice.repositories.dummys.DummyCardRepository;

@Component
public class CSVImporterService implements CommandLineRunner {

  private final Logger logger = LoggerFactory.getLogger(CSVImporterService.class);

  private DummyCardRepository dummyCardRepository;

  public CSVImporterService(DummyCardRepository dummyCardRepository) {
    this.dummyCardRepository = dummyCardRepository;
  }

  private static final String IMPORT_ARG = "import";

  @Override
  public void run(String... args) throws Exception {
    if (isImportInProgramArgs(args)) {
      logger.info("Importing from \"Datenbasis.csv\"");
      List<String[]> csvList = CSVReader.readCSV("ressources\\Datenbasis.csv");
      csvList.forEach(this::importCard);
    }
  }

  private boolean isImportInProgramArgs(String[] args) {
    return new HashSet<>(Arrays.asList(args)).contains(IMPORT_ARG);
  }

  private void importCard(String[] cardArray) {
    String name = cardArray[1] + " (" + cardArray[0] + ")";
    StringBuilder description = new StringBuilder();
    for (int i = 2; i < cardArray.length; i++) {
      description.append(cardArray[i]);
    }
    //only 255 chars are allowed for description
    if (description.length() > 255) {
      description = new StringBuilder(description.substring(0, 255));
    }
    dummyCardRepository.save(new DummyCard(name, description.toString()));
  }
}
