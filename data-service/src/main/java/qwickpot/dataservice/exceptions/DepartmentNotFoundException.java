package qwickpot.dataservice.exceptions;

public class DepartmentNotFoundException extends RuntimeException {

  public DepartmentNotFoundException(String detailMessage) {
    super(detailMessage);
  }
}
