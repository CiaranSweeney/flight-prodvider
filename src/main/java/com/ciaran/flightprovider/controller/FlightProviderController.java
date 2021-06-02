package com.ciaran.flightprovider.controller;

import com.ciaran.flightprovider.client.MockFlightClient;
import com.ciaran.flightprovider.exception.DateFormatException;
import com.ciaran.flightprovider.exception.ImpossibleDateException;
import com.ciaran.flightprovider.modle.FlightsSearch;
import com.ciaran.flightprovider.service.FlightProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;


@RestController
public class FlightProviderController {

  @Autowired
  private MockFlightClient mockFlightClient;

  @Autowired
  private FlightProviderService flightProviderService;

  @RequestMapping("/flight")
  public String flight(
          @RequestParam(value = "origin-airport") String originAirport,
          @RequestParam(value = "destination-airport") String destinationAirport,
          @RequestParam(value = "departure-date") String departureDate,
          @RequestParam(value = "return-date") String returnDate,
          @RequestParam(value = "num-passengers") int numPassengers
                       )
          throws DateFormatException, ParseException, ImpossibleDateException {

    FlightsSearch flightsSearch = new FlightsSearch(originAirport, destinationAirport,
            departureDate, returnDate, numPassengers);

    return flightProviderService.getFlights(flightsSearch);
  }

}
