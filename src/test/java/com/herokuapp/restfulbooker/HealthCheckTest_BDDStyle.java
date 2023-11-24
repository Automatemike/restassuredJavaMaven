package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class HealthCheckTest_BDDStyle extends BaseTest
{
    @Test
    public void healthCheckTest()
    {
        given().spec(spec).  //Spec represents a RequestSpecification in the BaseTest class
                when().get("/ping").
                then().assertThat().statusCode(201);
    }

    @Test
    public void headersAndCookiesTest()
    {
        Response response = RestAssured.given(spec).get("/ping");  //Instantiation of response

        //Get headers
        Headers headers = response.getHeaders();
        System.out.println("Headers:" + headers);

        Header serverHeader1 = headers.get("Server");
        System.out.println(serverHeader1.getName() + ": " + serverHeader1.getValue());

        String serverHeader2 = response.getHeader("Server");
        System.out.println("Server: " + serverHeader2);

        //Get Cookies
        Cookies cookies = response.getDetailedCookies();
        System.out.println("Cookies: " + cookies);
    }
}
