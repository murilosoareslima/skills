package com.ml.record.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

public class DeployVerifyIT {

    @Test
    public void deployVerify() {
        String succssesReturn = "Api is running";
        ValidatableResponse body = RestAssured.given().when().get("http://localhost:8080").then()        
        .assertThat().statusCode(200)
        .body(containsString(succssesReturn));
        assertEquals(succssesReturn, body.extract().asString());
    }
}