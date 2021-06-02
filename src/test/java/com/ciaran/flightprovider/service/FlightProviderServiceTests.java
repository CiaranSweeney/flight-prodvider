package com.ciaran.flightprovider.service;

import com.ciaran.flightprovider.client.MockFlightClient;
import com.ciaran.flightprovider.exception.DateFormatException;
import com.ciaran.flightprovider.exception.ImpossibleDateException;
import com.ciaran.flightprovider.modle.FlightsSearch;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FlightProviderServiceTests {

  @InjectMocks
  private FlightProviderService flightProviderService;

  @Mock
  private MockFlightClient mockFlightClient;

  @Test
  public void getFlightsTest() throws IOException, ParseException, ImpossibleDateException, DateFormatException, JSONException {
    FlightsSearch flightsSearch = new FlightsSearch("DUB","EDI","01-08-2021","01-09-2021",1);
    String xmlResponse = returnFileAsString("mock-flights-xml-response");
    when(mockFlightClient.getFlights(flightsSearch)).thenReturn(xmlResponse);
    String jsonResponse = returnFileAsString("mock-flights-json-response");

    assertEquals(jsonResponse, flightProviderService.getFlights(flightsSearch));
  }

  @Test
  public void dateFormatExceptionDepartureDateTest() {
    FlightsSearch flightsSearch = new FlightsSearch("DUB","EDI","01-08-wrong","01-09-2021",1);
    DateFormatException dateFormatException = assertThrows(DateFormatException.class, () -> {
      flightProviderService.getFlights(flightsSearch);
    });
    DateFormatException expectedDateFormatException = new DateFormatException("departure-date");
    assertEquals(expectedDateFormatException.getMessage(), dateFormatException.getMessage());
  }

  @Test
  public void dateFormatExceptionReturnDateTest() {
    FlightsSearch flightsSearch = new FlightsSearch("DUB","EDI","01-08-2021","01-09-wrong",1);
    DateFormatException dateFormatException = assertThrows(DateFormatException.class, () -> {
      flightProviderService.getFlights(flightsSearch);
    });
    DateFormatException expectedDateFormatException = new DateFormatException("return-date");
    assertEquals(expectedDateFormatException.getMessage(), dateFormatException.getMessage());
  }

  @Test
  public void impossibleFlightDatesTest() {
    FlightsSearch flightsSearch1 = new FlightsSearch("DUB","EDI","01-08-1900","01-08-2021",1);
    assertThrows(ImpossibleDateException.class, () -> {
      flightProviderService.getFlights(flightsSearch1);
    });

    FlightsSearch flightsSearch2 = new FlightsSearch("DUB","EDI","02-08-2021","01-08-2021",1);
    assertThrows(ImpossibleDateException.class, () -> {
      flightProviderService.getFlights(flightsSearch2);
    });
  }

  private  String returnFileAsString(final String filename) throws IOException {
    final ClassPathResource resource = new ClassPathResource(filename);
    return FileCopyUtils.copyToString(new InputStreamReader(resource.getInputStream()));
  }

}
