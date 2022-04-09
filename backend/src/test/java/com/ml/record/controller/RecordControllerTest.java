package com.ml.record.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import java.util.LinkedHashMap;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.basePath;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@SpringBootTest
public class RecordControllerTest {

    @Value("${server.host}")
    private String serverHost;

    @Value("${server.port}")
    private String serverPort;

    @Before
    public void setup() {
        baseURI = serverHost + ":" + serverPort;
        basePath = "/api/record";
    }

    @Test
    public void shouldPassCreateRecordValid() {
        String json = getRecordValidJson();
        LinkedHashMap<String, String> responseRecord = RestAssured.given()
                .body(json).contentType(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .path("data");
        assertNotNull(responseRecord);
        assertEquals("27348324029", responseRecord.get("cpf"));
    }

    @Test
    public void shouldPassFindValidCpf() {
        String json = getRecordValidJson();
        String cpf = RestAssured.given()
                .body(json).contentType(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .path("data.cpf");        

        LinkedHashMap<String, String> responseRecord = RestAssured
                .given()
                .when()
                .get("/" + cpf)
                .then()
                .extract()
                .body()
                .path("data");
        assertNotNull(responseRecord);
        assertEquals("Nome Exemplo", responseRecord.get("name"));
    }

    @Test
    public void shouldNotPassInvalidCpf() {
        String cpf = "1111111111";
        String messageErrorExpected = "O Cpf informado não é válido: " + cpf;
        RestAssured.given()
                .when()
                .get("/" + cpf)
                .then()
                .statusCode(400)
                .spec(new ResponseSpecBuilder()
                                .build()
                                .expect()
                                .body("errors.details", CoreMatchers.hasItem(messageErrorExpected)));
    }

    @Test
    public void shouldNotPassInvalidRecord() {
        String json = getRecordInvalidJson();
        String messageErrorExpected = "É necessário informar ao menos um endereço";
        RestAssured.given()
                .body(json).contentType(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(400)
                .spec(new ResponseSpecBuilder()
                                .build()
                                .expect()
                                .body("errors.details", CoreMatchers.hasItem(messageErrorExpected)));
    }

    private String getRecordValidJson() {
        return "{\"name\": \"Nome Exemplo\",\"age\": 18, \"cpf\" : 27348324029,\"phones\": [{\"ddd\": \"011\",\"number\": \"111111111\"},{\"ddd\": \"011\",\"number\": \"11111111\"}],\"addresses\": [{\"cep\": \"13300000\",\"street\": \"Rua Exemplo\",\"number\": 1000,\"district\": \"Bairro Exemplo\",\"city\": \"Cidade Exemplo\",\"state\": \"Estado Exemplo\",\"complement\": \"Complemento Exemplo\"}]}";
    }

    private String getRecordInvalidJson() {
        return "{\"name\": \"Nome Exemplo\",\"age\": 18, \"cpf\" : 27348324029,\"phones\": [{\"ddd\": \"011\",\"number\": \"111111111\"},{\"ddd\": \"011\",\"number\": \"11111111\"}]}";
    }
}
