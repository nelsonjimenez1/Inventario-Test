package com.inventory.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
   @ExceptionHandler(value = ProductException.class)
   public ResponseEntity exception(ProductException exception) {
      return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
   }

   @ExceptionHandler(value = UserException.class)
   public ResponseEntity exception(UserException exception) {
      return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
   }
}
