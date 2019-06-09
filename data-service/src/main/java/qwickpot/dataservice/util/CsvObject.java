package qwickpot.dataservice.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CsvObject {

  private List<String> head;
  private List<List<String>> lines;
  private String delimiter = ";";

  public CsvObject() {
    lines = new LinkedList<>();
  }

  public List<String> getHead() {
    return head;
  }

  public Iterator<List<String>> getLineIterator() {
    return lines.iterator();
  }

  public boolean addCsvLine(String line) {
    return lines.add(convertLine(line));
  }

  private List<String> convertLine(String line) {
    List<String> valuesList = Arrays.asList(line.split(delimiter));
    valuesList.replaceAll(a -> a.equals("") || a.equals("null") ? null : a);
    return valuesList;
  }

  public CsvObject withHead(String csvLine) {
    this.head = convertLine(csvLine);
    return this;
  }

  public CsvObject withDelimiter(String delimiter) {
    this.delimiter = delimiter;
    return this;
  }
}
