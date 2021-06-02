package com.ciaran.flightprovider.modle;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FlightsSearch {
  private String originAirport;
  private String destinationAirport;
  private String departureDate;
  private String returnDate;
  private int num_passengers;
}
