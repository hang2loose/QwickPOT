package qwickpot.dataservice.dtos;

public interface CsvReadable<T> {

  T convertFromCsvLine(String[] stringArray);
}
