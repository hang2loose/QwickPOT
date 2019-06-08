package qwickpot.dataservice.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

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
}