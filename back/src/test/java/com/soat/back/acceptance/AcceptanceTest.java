package com.soat.back.acceptance;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;

public class AcceptanceTest {

    @LocalServerPort
    protected int port;

    public Response response;

    public void executeGet(String url) {
        response = given()
                .log().all()
                .header("Content-Type", ContentType.JSON)
                .when()
                .get(url);
    }

    public void executePost(String url, Object payload) {
        response = given()
                .log().all()
                .header("Content-Type", ContentType.JSON)
                .body(payload)
                .when()
                .post(url);
    }
}
