package com.ciaran.flightprovider.exception;

public class DateFormatException extends Exception {

  private static final String DATE_ERROR_MESSAGE =
          "Error %s has incorrect format, it should be in DD/MM/YYYY format";

  public DateFormatException(String flight) {
    super(String.format(DATE_ERROR_MESSAGE, flight));
  }

}
