package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DeleteBookingTests extends BaseTest
{
    @Test
    public void deleteBookingTest()
    {
        //Create booking using BaseTest class
        Response responseCreate = createBooking();
        responseCreate.print();

        //Get bookingId of new booking
        int bookingid = responseCreate.jsonPath().getInt("bookingid");

        //Run(apply) deletion of the booking record
        Response responseDeletion = RestAssured.given(spec).auth().preemptive().basic("admin", "password123").
                delete("/booking/" + bookingid);
        responseDeletion.print();

        //Verifications
        //Verify get a 200 back
        Assert.assertEquals(responseDeletion.getStatusCode(), 201, "Status code should be 200, but it's not");

        Response responseGet = RestAssured.get("https://restful-booker.herokuapp.com/booking/" + bookingid);
        responseGet.print();

        Assert.assertEquals(responseGet.getBody().asString(), "Not Found", "Body should be 'Not Found'");
    }
}
