package com.soat.back;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.springframework.boot.web.server.LocalServerPort;

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
}
