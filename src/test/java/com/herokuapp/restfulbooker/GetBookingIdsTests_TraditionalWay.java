package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class GetBookingIdsTests_TraditionalWay extends BaseTest
{
    @Test
    public void getBookingIdsWithoutFilterTest()
    {
        //Get response with booking ids
        Response response = RestAssured.given(spec).get("/booking");
        response.print();

        //Verify response 200
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200, but it's not");

        //Verify at least 1 booking id is in response
        List<Integer> bookingIds = response.jsonPath().getList("bookingId");
        Assert.assertFalse(bookingIds.isEmpty(), "List of bookingIds is empty, but it shouldn't be");

        //Can check out Query params as well to filter for specific bookings.
    }
}
