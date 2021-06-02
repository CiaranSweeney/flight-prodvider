package com.ciaran.flightprovider.client;

import com.ciaran.flightprovider.modle.FlightsSearch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class MockFlightClientTests {

  @InjectMocks
  private MockFlightClient mockFlightClient;

  @Mock
  private RestTemplate restTemplate = new RestTemplate();

  private String url =
          "http://private-anon-8783adcf3a-mockairline.apiary-mock.com/flights/DUB/EDI/01-01-2014/01-01-2014/1";


  @Test
  public void getFlightsTest() throws IOException {

    FlightsSearch flightsSearch = new FlightsSearch("DUB","EDI","01-01-2014","01-01-2014",1);
    String xmlResponse = returnFileAsString("mock-flights-xml-response");
    when(restTemplate.getForObject(url,String.class)).thenReturn(xmlResponse);

    assertEquals(xmlResponse, mockFlightClient.getFlights(flightsSearch));
  }

  private  String returnFileAsString(final String filename) throws IOException {
    final ClassPathResource resource = new ClassPathResource(filename);
    return FileCopyUtils.copyToString(new InputStreamReader(resource.getInputStream()));
  }

}
