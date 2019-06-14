package qwickpot.dataservice.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CSVReader {

  private CSVReader() {
  }

  public static Optional<CsvObject> getCsv(String csvFile) {
    try (Scanner scanner = new Scanner(new File(csvFile))) {
      CsvObject csvObject = new CsvObject().withHead(scanner.nextLine());
      do {
        csvObject.addCsvLine(scanner.nextLine());
      } while (scanner.hasNext());
      csvObject.getLineIterator()
          .forEachRemaining(line -> log.info("added: " + line.toString()));
      return Optional.of(csvObject);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }
}