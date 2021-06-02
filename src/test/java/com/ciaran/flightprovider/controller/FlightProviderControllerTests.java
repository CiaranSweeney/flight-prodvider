package com.ciaran.flightprovider.controller;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FlightProviderControllerTests {

  private final String url =
          "/flight?origin-airport=%s&destination-airport=%s&departure-date=%s&return-date=%s&num-passengers=%s";

  @LocalServerPort
  int port;

  @Before
  public void setUp() {
    RestAssured.port = port;
  }

  @Test
  public void getFlightSuccessfulCall(){
    given().when()
            .get(url.format(url,"DUB","EDI","01-10-2022","01-12-2022","3"))
            .then().
            assertThat()
            .statusCode(200);
  }

  @Test
  public void getFlightErrorWithIncorrectDateFormat(){
    given().when()
            .get(url.format(url,"DUB","EDI","wrong","01-12-2022","3"))
            .then()
            .assertThat()
            .statusCode(400);
  }

  @Test
  public void getFlightErrorWithImpossibleDate(){
    given().when()
            .get(url.format(url,"DUB","EDI","0-10-2000","01-12-2022","3"))
            .then()
            .assertThat()
            .statusCode(400);

    given().when()
            .get(url.format(url,"DUB","EDI","0-10-2020","01-09-2022","3"))
            .then()
            .assertThat()
            .statusCode(400);
  }

}
