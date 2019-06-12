package qwickpot.dataservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User not found")
public class CardNotFoundException extends RuntimeException {

  public CardNotFoundException(String detailMessage) {
    super(detailMessage);
  }
}

