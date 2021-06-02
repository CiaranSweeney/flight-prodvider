package com.ciaran.flightprovider.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

  private String bodyOfResponse = "%s:%s";

  @ExceptionHandler({DateFormatException.class, ImpossibleDateException.class})
  protected ResponseEntity<Object> handleDateTimeParseException(Exception ex, WebRequest request) {
    bodyOfResponse =  bodyOfResponse.format(bodyOfResponse,HttpStatus.BAD_REQUEST.toString(),ex.getMessage());

    return handleExceptionInternal(ex, bodyOfResponse,
            new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler({HttpClientErrorException.class})
  protected ResponseEntity<Object> handleHttpClientErrorException(Exception ex, WebRequest request) {
    bodyOfResponse =  bodyOfResponse.format(bodyOfResponse,HttpStatus.BAD_GATEWAY.toString(),ex.getMessage());
    return handleExceptionInternal(ex, bodyOfResponse,
            new HttpHeaders(), HttpStatus.BAD_GATEWAY, request);
  }
}
