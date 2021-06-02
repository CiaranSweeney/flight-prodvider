package com.ciaran.flightprovider.exception;

public class ImpossibleDateException extends Exception {

  private static final String IMPOSSIBLE_DATE_EXCEPTION_MESSAGE = "The flight date of departure-date or " +
          "return-date is impossible. This can be due to the return-date being before the departure-date or" +
          " one of the entered dates being before today's date.";

  public ImpossibleDateException() {
    super(IMPOSSIBLE_DATE_EXCEPTION_MESSAGE);
  }
}
