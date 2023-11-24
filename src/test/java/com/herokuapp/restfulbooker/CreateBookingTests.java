package com.herokuapp.restfulbooker;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CreateBookingTests extends BaseTest
{
    @Test
    public void createBookingTests()
    {
        Response response = createBooking();
        response.print();

        //Verifications
        //Verify get a 200 back
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200, but it's not");

        //Verify All fields
        SoftAssert softAssert = new SoftAssert();
        String actualFirstName = response.jsonPath().getString("booking.firstname");
        softAssert.assertEquals(actualFirstName, "TestFirstName", "firstname in response is not expected");

        String actualLastName = response.jsonPath().getString("booking.lastname");
        softAssert.assertEquals(actualLastName, "TestLastName", "lastname in response is not expected");

        int price = response.jsonPath().getInt("booking.totalprice");
        softAssert.assertEquals(price, 180, "totalprice is response is not expected");

        boolean depositpage = response.jsonPath().getBoolean("booking.depositpaid");
        softAssert.assertFalse(depositpage, "depositpaid should be false, but it's not");

        String actualCheckin = response.jsonPath().getString("booking.bookingdates.checkin");
        softAssert.assertEquals(actualCheckin, "2024-02-01", "checkin in response is not expected");

        String actualCheckout = response.jsonPath().getString("booking.bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, "2024-02-15", "checkout in response is not expected");

        String actualAdditionalneeds = response.jsonPath().getString("booking.additionalneeds");
        softAssert.assertEquals(actualAdditionalneeds, "Breakfast", "additionalneeds in response is not expected");

        softAssert.assertAll();

        System.out.println("Passed as all data is as expected");
    }
}
