package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class PartialUpdateBookingTests extends BaseTest
{
    @Test
    public void partialUpdateBookingTest()
    {
        //Create booking using BaseTest class
        Response responseCreate = createBooking();
        responseCreate.print();

        //Get bookingId of new booking
        int bookingid = responseCreate.jsonPath().getInt("bookingid");

        //Create JSON body for a partial update including firstname and checkin
        JSONObject body = new JSONObject();
        body.put("firstname", "TestFirstNamePartialUpdate");

        JSONObject bookingdates = new JSONObject();
        bookingdates.put("checkin", "2024-02-02");
        bookingdates.put("checkout", "2024-02-15"); //Have to have this in as otherwise will be updated with nothing.
        body.put("bookingdates", bookingdates);  //Need this in as otherwise dates, incl. updates won't be included in body.

        //Run(apply) the partial update
        Response responsePartialUpdate = RestAssured.given(spec).
                auth().preemptive().basic("admin", "password123").
                contentType(ContentType.JSON).body(body.toString()).
                patch("/booking/" + bookingid);
        responsePartialUpdate.print();

        //Verifications
        //Verify get a 200 back
        Assert.assertEquals(responsePartialUpdate.getStatusCode(), 200, "Status code should be 200, but it's not");

        //Verify All fields
        SoftAssert softAssert = new SoftAssert();
        String actualFirstName = responsePartialUpdate.jsonPath().getString("firstname");
        softAssert.assertEquals(actualFirstName, "TestFirstNamePartialUpdate", "firstname in response is not expected");

        String actualLastName = responsePartialUpdate.jsonPath().getString("lastname");
        softAssert.assertEquals(actualLastName, "TestLastName", "lastname in response is not expected");

        int price = responsePartialUpdate.jsonPath().getInt("totalprice");
        softAssert.assertEquals(price, 180, "totalprice is response is not expected");

        boolean depositpaid = responsePartialUpdate.jsonPath().getBoolean("depositpaid");
        softAssert.assertFalse(depositpaid, "depositpaid should be false, but it's not");

        String actualCheckin = responsePartialUpdate.jsonPath().getString("bookingdates.checkin");
        softAssert.assertEquals(actualCheckin, "2024-02-02", "checkin in response is not expected");

        String actualCheckout = responsePartialUpdate.jsonPath().getString("bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, "2024-02-15", "checkout in response is not expected");

        String actualAdditionalneeds = responsePartialUpdate.jsonPath().getString("additionalneeds");
        softAssert.assertEquals(actualAdditionalneeds, "Breakfast", "additionalneeds in response is not expected");

        softAssert.assertAll();
    }
}
