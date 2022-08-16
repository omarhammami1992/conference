package com.soat.back.acceptance;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.soat.back.common.infrastructure.JpaConferenceRepository;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.RestAssured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.format.DateTimeFormatter;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@DirtiesContext
@CucumberContextConfiguration
@ActiveProfiles("AcceptanceTest")
public class ConferenceSteps extends AcceptanceTest {

    private static final String API_CONFERENCE = "/conference";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Before
    public void before() {
        RestAssured.port = port;
        RestAssured.basePath = API_CONFERENCE;
    }

    @Given("une conférence ayant le nom {string}, le lien {string} et qui dure entre {string} et {string}")
    public void uneConférenceAvantLeNomLeLienEtQuiDureEntreEt(String name, String link, String startDate, String endDate) {

    }

    @When("l utilisateur tente de l enregistrer")
    public void lUtilisateurTenteDeLEnregistrer() {

    }

    @Then("la conférence est enregistée avec le nom {string}, le lien {string} et qui dure entre {string} et {string}")
    public void laConférenceEstEnregistéeAvecLeNomLeLienEtQuiDureEntreEt(String name, String link, String startDate, String endDate) {

    }
}
