package com.capsulewardrobe.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

  private Logger logger = LoggerFactory.getLogger(getClass());

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(value = EntityNotFoundException.class)
  public ApiError handleNotFound(EntityNotFoundException e) {

    return ApiError.builder()
            .message(e.getMessage())
            .status(HttpStatus.NOT_FOUND)
            .build();
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(value = BadRequestException.class)
  public ApiError handleBadRequest(BadRequestException e) {

    return ApiError.builder()
            .message(e.getMessage())
            .status(HttpStatus.BAD_REQUEST)
            .build();
  }

  @Override
  public ResponseEntity<Object> handleMethodArgumentNotValid(
          MethodArgumentNotValidException e,
          HttpHeaders headers,
          HttpStatus status,
          WebRequest request
  ) {
    List<String> causes = new ArrayList<>();

    for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
      String formattedMessage = String.format("Property %s %s", fieldError.getField(), fieldError.getDefaultMessage());
      causes.add(formattedMessage);
    }

    ApiError error = ApiError.builder()
            .message(String.join(", ", causes))
            .status(HttpStatus.BAD_REQUEST)
            .build();

    return new ResponseEntity<>(error, null, HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
          HttpMessageNotReadableException e,
          HttpHeaders headers,
          HttpStatus status,
          WebRequest request
  ) {
    ApiError error = ApiError.builder()
            .message("Malformed JSON request.")
            .status(HttpStatus.BAD_REQUEST)
            .build();

    return new ResponseEntity<>(error, null, HttpStatus.BAD_REQUEST);
  }

}
