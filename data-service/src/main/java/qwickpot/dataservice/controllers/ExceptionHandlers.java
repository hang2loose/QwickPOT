package qwickpot.dataservice.controllers;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import qwickpot.dataservice.exceptions.CardNotFoundException;
import qwickpot.dataservice.exceptions.DepartmentNotFoundException;
import qwickpot.dataservice.exceptions.ThemeNotFoundException;

@ControllerAdvice
@Slf4j
public class ExceptionHandlers {

  @ExceptionHandler(ThemeNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  public ErrorResponse handleThemeNotFoundException(final ThemeNotFoundException ex) {
    log.error("User not found thrown", ex);
    return new ErrorResponse("THEME_NOT_FOUND", ex.getMessage());
  }

  @ExceptionHandler(CardNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  public ErrorResponse handleCardNotFoundException(final CardNotFoundException ex) {
    log.error("Card not found thrown", ex);
    return new ErrorResponse("CARD_NOT_FOUND", ex.getMessage());
  }

  @ExceptionHandler(DepartmentNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  public ErrorResponse handleCardNotFoundException(final DepartmentNotFoundException ex) {
    log.error("Department not found thrown", ex);
    return new ErrorResponse("DEPARTMENT_NOT_FOUND", ex.getMessage());
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Throwable.class)
  @ResponseBody
  public ErrorResponse handleThrowable(final Throwable ex) {
    log.error("Unexpected error", ex);
    return new ErrorResponse("INTERNAL_SERVER_ERROR",
        "An unexpected internal server error occured");
  }

  @Data
  public static class ErrorResponse {

    private final String code;
    private final String message;
  }
}
