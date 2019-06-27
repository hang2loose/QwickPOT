package qwickpot.dataservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Department not found")
public class DepartmentNotFoundException extends RuntimeException {

  public DepartmentNotFoundException(String detailMessage) {
    super(detailMessage);
  }
}
