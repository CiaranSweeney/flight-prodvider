package com.ciaran.flightprovider.client;

import com.ciaran.flightprovider.modle.FlightsSearch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class MockFlightClient {

  private final static String URL =
          "http://private-anon-8783adcf3a-mockairline.apiary-mock.com/flights/%s/%s/%s/%s/%s";

  @Autowired
  private RestTemplate restTemplate;

  public String getFlights(FlightsSearch flightsSearch) {
    String fullUrl = URL.format(URL, flightsSearch.getOriginAirport(),
            flightsSearch.getDestinationAirport(), flightsSearch.getDepartureDate(),
            flightsSearch.getReturnDate() , flightsSearch.getNum_passengers());
    String flightsResponse = null;
    try {
      flightsResponse = restTemplate.getForObject(fullUrl,String.class);
      } catch (HttpClientErrorException e) {
        log.error("message=" +  e.getStackTrace());
        throw new HttpClientErrorException(HttpStatus.BAD_GATEWAY);
    }
    return flightsResponse;
  }

}
