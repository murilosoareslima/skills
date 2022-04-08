package com.ml.record.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeployVerifyIT {

    @Value("${server.host}")
    private String serverHost;

    @Value("${server.port}")
    private String serverPort;

    @Before
    public void setup() {
        RestAssured.baseURI = serverHost + ":" + serverPort;
    }

    @Test
    public void testDeployVerify() {
        String succssesReturn = "Api is running";
        ValidatableResponse body = RestAssured.given().when().get("/").then()        
        .assertThat().statusCode(200)
        .body(containsString(succssesReturn));
        
        assertEquals(succssesReturn, body.extract().asString());
    }
}