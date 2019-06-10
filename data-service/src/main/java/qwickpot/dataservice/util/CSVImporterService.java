package qwickpot.dataservice.util;

import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import qwickpot.dataservice.domain.dummys.DummyCard;
import qwickpot.dataservice.repositories.dummys.DummyCardRepository;

@Component
@Order(value = 1)
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
      LinkedList<String[]> csvList = CSVReader.readCSV("ressources\\Datenbasis.csv");
      csvList.forEach(e -> importCard(e));
    }
  }

  private boolean isImportInProgramArgs(String[] args) {
    boolean result = false;
    for (int i = 0; i < args.length; i++) {
      if (args[i].equalsIgnoreCase(IMPORT_ARG)) {
        result = true;
      }
    }
    return result;
  }

  private void importCard(String[] cardArray) {
    String name = cardArray[1] + " (" + cardArray[0] + ")";
    String description = "";
    for (int i = 2; i < cardArray.length; i++) {
      description += cardArray[i];
    }
    //only 255 chars are allowed for description
    if (description.length() > 255) {
      description = description.substring(0, 255);
    }
    dummyCardRepository.save(new DummyCard(name, description));
  }
}
