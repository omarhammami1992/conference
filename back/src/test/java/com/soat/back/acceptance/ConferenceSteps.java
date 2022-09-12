package com.soat.back.acceptance;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.RestAssured;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.soat.back.common.infrastructure.JpaConference;
import com.soat.back.common.infrastructure.JpaConferenceRepository;
import com.soat.back.conference.command.application.ConferenceToSaveJson;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@DirtiesContext
@CucumberContextConfiguration
@ActiveProfiles("AcceptanceTest")
public class ConferenceSteps extends AcceptanceTest {

    private static final String API_CONFERENCE = "/conference";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private ConferenceToSaveJson conferenceToSaveJson;

    @Autowired
    private JpaConferenceRepository jpaConferenceRepository;

    @Before
    public void before() {
        RestAssured.port = port;
        RestAssured.basePath = API_CONFERENCE;
    }

    @Given("une conférence ayant le nom {string}, le lien {string} et qui dure entre {string} et {string}")
    public void uneConférenceAvantLeNomLeLienEtQuiDureEntreEt(String name, String link, String startDate, String endDate) {
        conferenceToSaveJson = new ConferenceToSaveJson(name, link, startDate, endDate);
    }

    @When("l utilisateur tente de l enregistrer")
    public void lUtilisateurTenteDeLEnregistrer() throws JsonProcessingException {
        executePost("", conferenceToSaveJson);
    }


    @Then("la conférence est enregistée")
    public void laConférenceEstEnregistée() {
        final Integer savedConferenceId = response.then().extract().as(Integer.class);
        JpaConference jpaConference = jpaConferenceRepository.findById(savedConferenceId).orElse(null);
        JpaConference expectedJpaConference = new JpaConference(
              savedConferenceId,
              conferenceToSaveJson.name(),
              conferenceToSaveJson.link(),
              LocalDate.parse(conferenceToSaveJson.startDate(), DATE_TIME_FORMATTER),
              LocalDate.parse(conferenceToSaveJson.endDate(), DATE_TIME_FORMATTER));

        assertThat(expectedJpaConference).usingRecursiveComparison().isEqualTo(jpaConference);
    }
}
