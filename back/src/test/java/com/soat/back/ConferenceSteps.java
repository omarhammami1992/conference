package com.soat.back;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.soat.back.common.infrastructure.JpaConference;
import com.soat.back.common.infrastructure.JpaConferenceRepository;
import com.soat.back.conference.command.application.ConferenceToSaveJson;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@DirtiesContext
@CucumberContextConfiguration
@ActiveProfiles("AcceptanceTest")
public class ConferenceSteps extends AcceptanceTest {

    private static final String API_CONFERENCE = "/api/conference";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private String name;
    private String link;
    private String startDate;
    private String endDate;

    @Autowired
    private JpaConferenceRepository jpaConferenceRepository;

    @Before
    public void before() {
        RestAssured.port = port;
        RestAssured.basePath = API_CONFERENCE;
    }

    @Given("une conférence avant le nom {string}, le lien {string} et qui dure entre {string} et {string}")
    public void uneConférenceAvantLeNomLeLienEtQuiDureEntreEt(String name, String link, String startDate, String endDate) {
        this.name = name;
        this.link = link;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @When("l utilisateur tente de l enregistrer")
    public void lUtilisateurTenteDeLEnregistrer() throws JsonProcessingException {
        final ConferenceToSaveJson conferenceToSaveJson = new ConferenceToSaveJson(name, link, startDate, endDate);
        executePost("", conferenceToSaveJson);
    }

    @Then("la conférence est enregistée avec le nom {string}, le lien {string} et qui dure entre {string} et {string}")
    public void laConférenceEstEnregistéeAvecLeNomLeLienEtQuiDureEntreEt(String name, String link, String startDate, String endDate) {
        final Integer savedConferenceId = response.then().extract().as(Integer.class);
        final JpaConference savedConference = jpaConferenceRepository.findById(savedConferenceId).orElse(null);
        JpaConference expectedSavedConference = new JpaConference(
                savedConferenceId,
                name,
                link,
                LocalDate.parse(startDate, DATE_TIME_FORMATTER),
                LocalDate.parse(endDate, DATE_TIME_FORMATTER)
        );
        assertThat(savedConference).usingRecursiveComparison().isEqualTo(expectedSavedConference);
    }
}
