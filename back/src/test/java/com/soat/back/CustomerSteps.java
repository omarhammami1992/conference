package com.soat.back;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.RestAssured;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@DirtiesContext
@CucumberContextConfiguration
@ActiveProfiles("AcceptanceTest")
public class CustomerSteps extends AcceptanceTest {

    @Before
    public void before() {
        RestAssured.port = port;
        RestAssured.basePath = "/api/test";
    }

    @When("the client calls {string}")
    public void the_client_calls(String url) {
        executeGet(url);
    }

    @Then("the client receives as status code {int}")
    public void theClientReceivesStatusCode(int statusCode) {
        assertThat(response.getStatusCode()).isEqualTo(statusCode);
    }

    @And("the client receives as body (.+)$")
    public void the_client_receives_as_body_hello_world(String body) {
        assertThat(response.body().asString()).isEqualTo(body);
    }
}
