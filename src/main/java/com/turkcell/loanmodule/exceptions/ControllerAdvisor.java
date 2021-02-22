package com.turkcell.loanmodule.exceptions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

  private static final String MESSAGE = "message";
  private static final String TIMESTAMP = "timestamp";

  @ExceptionHandler(CustomerOnBlacklistException.class)
  public ResponseEntity<Object> handleCustomerOnBlacklistException(
      CustomerOnBlacklistException ex) {

    Map<String, Object> body = new LinkedHashMap<>();
    body.put(TIMESTAMP, LocalDateTime.now());
    body.put(MESSAGE, ex.getMessage());

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MissingDocumentException.class)
  public ResponseEntity<Object> handleMissingDocumentException(
      MissingDocumentException ex) {

    Map<String, Object> body = new LinkedHashMap<>();
    body.put(TIMESTAMP, LocalDateTime.now());
    body.put(MESSAGE, ex.getMessage());

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }


  @ExceptionHandler(DataIntegrityViolationException.class)
  @ResponseStatus(HttpStatus.BAD_GATEWAY)
  @ResponseBody
  public ResponseEntity<Object> handleDataIntegrityViolationException(
      final DataIntegrityViolationException ex) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put(TIMESTAMP, LocalDateTime.now());
    String message;
    if (ex.getRootCause().getMessage().contains("Ayrıntı")) {
      message = ex.getRootCause().getMessage().split("Ayrıntı")[ 1 ]
          .replace("/", "").replace("\"", " ");
    } else {
      message = ex.getRootCause().getMessage();
    }
    body.put(MESSAGE, message);
    return new ResponseEntity<>(body, HttpStatus.CONFLICT);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex, HttpHeaders headers,
      HttpStatus status, WebRequest request) {

    Map<String, Object> body = new LinkedHashMap<>();
    body.put(TIMESTAMP, LocalDate.now());
    body.put("status", status.value());

    List<String> errors = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(x -> x.getDefaultMessage())
        .collect(Collectors.toList());

    body.put("errors", errors);

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }
}
