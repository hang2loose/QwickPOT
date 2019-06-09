package qwickpot.dataservice.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CSVReader {

  private static final String CSV_DELIMITER = ";";
  private static final int CSV_MIN_COLUMN_COUNT = 3;

  private CSVReader() {
  }

  public static List<String[]> readCSV(String csvFile) {
    List<String[]> result = new LinkedList<>();
    String line = "";
    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
      while ((line = br.readLine()) != null) {
        String[] entryArray = line.split(CSV_DELIMITER);
        if (entryArray.length > CSV_MIN_COLUMN_COUNT - 1) {
          result.add(entryArray);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }


  public static Optional<CsvObject> getCsv(String csvFile) {

    try (Scanner scanner = new Scanner(new File(csvFile))) {
      // set Head
      CsvObject csvObject = new CsvObject(scanner.nextLine());
      do {
        csvObject.addCsvLine(scanner.nextLine());
      } while (scanner.hasNext());
      csvObject.getLineiterator()
          .forEachRemaining(line -> log.info("added: " + line.toString()));
      return Optional.of(csvObject);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }

}