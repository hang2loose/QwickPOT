package qwickpot.dataservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Theme not found")
public class ThemeNotFoundException extends RuntimeException {

  public ThemeNotFoundException(String detailMessage) {
    super(detailMessage);
  }
}

