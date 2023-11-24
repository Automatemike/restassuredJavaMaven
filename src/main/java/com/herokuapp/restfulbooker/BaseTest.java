package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;

public class BaseTest
{
    protected RequestSpecification spec;

    @BeforeMethod
    public void setUp(){
        spec = new RequestSpecBuilder().setBaseUri("https://restful-booker.herokuapp.com").
                build();
    }

    protected Response createBooking()
    {
        //Create JSON body
        JSONObject body = new JSONObject();
        body.put("firstname", "TestFirstName");
        body.put("lastname", "TestLastName");
        body.put("totalprice", 180);
        body.put("depositpaid", false);

        JSONObject bookingdates = new JSONObject();
        bookingdates.put("checkin", "2024-02-01");
        bookingdates.put("checkout", "2024-02-15");
        body.put("bookingdates", bookingdates);
        body.put("additionalneeds", "Breakfast");

        //Post (apply) JSON and get(return) response
        Response response = RestAssured.given(spec).contentType(ContentType.JSON).
                body(body.toString()).post("/booking");
        return response;
    }
}
