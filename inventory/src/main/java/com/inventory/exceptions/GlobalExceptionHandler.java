package com.inventory.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(value = UserException.class)
  public ResponseEntity<String> exception(UserException exception) {
    return new ResponseEntity<String>(exception.getMessage(), HttpStatus.BAD_REQUEST);
  }
}
