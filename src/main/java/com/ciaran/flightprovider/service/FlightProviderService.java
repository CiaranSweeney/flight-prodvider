package com.ciaran.flightprovider.service;

import com.ciaran.flightprovider.client.MockFlightClient;
import com.ciaran.flightprovider.exception.DateFormatException;
import com.ciaran.flightprovider.exception.ImpossibleDateException;
import com.ciaran.flightprovider.modle.FlightsSearch;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Date;

@Slf4j
@Component
public class FlightProviderService {

  @Autowired
  private MockFlightClient mockFlightClient;

  public String getFlights(FlightsSearch flightsSearch) throws DateFormatException, ParseException, ImpossibleDateException {
    if (!flightDatePatternCheck(flightsSearch.getDepartureDate())) {
      log.error("message= Date format error for departure-date");
      throw new DateFormatException("departure-date");
    }
    if (!flightDatePatternCheck(flightsSearch.getReturnDate())) {
      log.error("message= Date format error for return date");
      throw new DateFormatException("return-date");
    }
    if (!possibleFlightDates(flightsSearch.getDepartureDate(), flightsSearch.getReturnDate())) {
      log.error("message= An impossible date was used");
      throw new ImpossibleDateException();
    }
    String xmlResponse = mockFlightClient.getFlights(flightsSearch);
    JSONObject xmlJSONObj = XML.toJSONObject(xmlResponse);
    String jsonPrettyPrintString = xmlJSONObj.toString();
    return jsonPrettyPrintString;
  }

  private boolean flightDatePatternCheck(String flightDate) {
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-uuuu")
            .withResolverStyle(ResolverStyle.STRICT);
    try {
      dateFormatter.parse(flightDate);
      } catch (DateTimeParseException e) {
      log.error("message=Error occurred in parsing Date value");
      return false;
    }
    return true;
  }

  private boolean possibleFlightDates(String departureString, String returnString) throws ParseException {
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    Date departureDate = formatter.parse(departureString);
    Date returnDate = formatter.parse(returnString);
    Date today = new Date();
    if(departureDate.before(today) || returnDate.before(departureDate)) {
      return false;
    }
    return true;
  }

}
