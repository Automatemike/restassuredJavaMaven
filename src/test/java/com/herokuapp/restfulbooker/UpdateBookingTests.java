package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class UpdateBookingTests extends BaseTest
{
    @Test
    public void UpdateBookingTest()
    {
        //Create booking
        Response responseCreate = createBooking();
        responseCreate.print();

        //Get bookingId of new booking
        int bookingid = responseCreate.jsonPath().getInt("bookingid");

        //Create JSON body for an update
        JSONObject body = new JSONObject();
        body.put("firstname", "TestFirstName1");
        body.put("lastname", "TestLastName");
        body.put("totalprice", 150);
        body.put("depositpaid", true);

        JSONObject bookingdates = new JSONObject();
        bookingdates.put("checkin", "2024-02-01");
        bookingdates.put("checkout", "2024-02-15");
        body.put("bookingdates", bookingdates);
        body.put("additionalneeds", "Breakfast");

        //Run(apply) the update
        Response responseUpdate = RestAssured.given(spec).
                auth().preemptive().basic("admin", "password123").
                contentType(ContentType.JSON).body(body.toString()).
                put("/booking/" + bookingid);
        responseUpdate.print();

        //Verifications
        //Verify get a 200 back
        Assert.assertEquals(responseUpdate.getStatusCode(), 200, "Status code should be 200, but it's not");

        //Verify All fields
        SoftAssert softAssert = new SoftAssert();
        String actualFirstName = responseUpdate.jsonPath().getString("firstname");
        softAssert.assertEquals(actualFirstName, "TestFirstName1", "firstname in response is not expected");

        String actualLastName = responseUpdate.jsonPath().getString("lastname");
        softAssert.assertEquals(actualLastName, "TestLastName", "lastname in response is not expected");

        int price = responseUpdate.jsonPath().getInt("totalprice");
        softAssert.assertEquals(price, 150, "totalprice is response is not expected");

        boolean depositpaid = responseUpdate.jsonPath().getBoolean("depositpaid");
        softAssert.assertTrue(depositpaid, "depositpaid should be false, but it's not");

        String actualCheckin = responseUpdate.jsonPath().getString("bookingdates.checkin");
        softAssert.assertEquals(actualCheckin, "2024-02-01", "checkin in response is not expected");

        String actualCheckout = responseUpdate.jsonPath().getString("bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, "2024-02-15", "checkout in response is not expected");

        String actualAdditionalneeds = responseUpdate.jsonPath().getString("additionalneeds");
        softAssert.assertEquals(actualAdditionalneeds, "Breakfast", "additionalneeds in response is not expected");

        softAssert.assertAll();
    }
}
