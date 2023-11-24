package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class GetBookingTest_TraditionalWay extends BaseTest
{
    @Test
    public void getNameOfABookingTest()
    {
        //Create booking
        Response responseCreate = createBooking();
        responseCreate.print();

        //Set path parameter
        spec.pathParam("bookingId", responseCreate.jsonPath().getInt("bookingid"));

        //Get response for the created booking
        Response response = RestAssured.given(spec).get("/booking/{bookingId}");  //Spec represents a RequestSpecification in the BaseTest class
        response.print();

        //Verify get a 200 back
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200, but it's not");
        System.out.println("Passed as response returned successfully");

        //Verify All fields
        SoftAssert softAssert = new SoftAssert();
        String actualFirstName = response.jsonPath().getString("firstname");
        softAssert.assertEquals(actualFirstName, "TestFirstName", "firstname in response is not expected");

        String actualLastName = response.jsonPath().getString("lastname");
        softAssert.assertEquals(actualLastName, "TestLastName", "lastname in response is not expected");

        int price = response.jsonPath().getInt("totalprice");
        softAssert.assertEquals(price, 180, "totalprice is response is not expected");

        boolean depositpage = response.jsonPath().getBoolean("depositpaid");
        softAssert.assertFalse(depositpage, "depositpaid should be false, but it's not");

        String actualCheckin = response.jsonPath().getString("bookingdates.checkin");
        softAssert.assertEquals(actualCheckin, "2024-02-01", "checkin in response is not expected");

        String actualCheckout = response.jsonPath().getString("bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, "2024-02-15", "checkout in response is not expected");

        softAssert.assertAll();

        System.out.println("Passed as all data is as expected");
    }
}
