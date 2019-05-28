package qwickpot.dataservice.controllers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class CSVReader {
  public static final String CSV_DELIMITER = ";";
  public static final int CSV_MIN_COLUMN_COUNT = 3;

  public static LinkedList<String[]> readCSV(String csvFile) {
    BufferedReader br = null;
    String line = "";
    LinkedList<String[]> result = new LinkedList<>();
    try {
      br = new BufferedReader(new FileReader(csvFile));
      while ((line = br.readLine()) != null) {
        String[] entryArray = line.split(CSV_DELIMITER);
        if (entryArray.length > CSV_MIN_COLUMN_COUNT - 1) {
          result.add(entryArray);
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return result;
  }
}