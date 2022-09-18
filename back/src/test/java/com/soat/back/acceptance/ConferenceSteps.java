package com.soat.back.acceptance;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.RestAssured;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.soat.back.common.infrastructure.JpaConference;
import com.soat.back.common.infrastructure.JpaConferenceRepository;
import com.soat.back.common.infrastructure.JpaPricingRangeRepository;
import com.soat.back.conference.command.application.ConferenceJson;
import com.soat.back.conference.command.application.PricingRangeJson;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@DirtiesContext
@CucumberContextConfiguration
@ActiveProfiles("AcceptanceTest")
public class ConferenceSteps extends AcceptanceTest {

    private static final List<PricingRangeJson> pricingRanges = new ArrayList<>();
    private static final String API_CONFERENCE = "/conference";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private ConferenceJson conferenceJson;

    @Autowired
    private JpaConferenceRepository jpaConferenceRepository;

    @Autowired
    private JpaPricingRangeRepository jpaPricingRangeRepository;

    private String name;
    private String link;
    private String sartDate;
    private String endDate;

    @Before
    public void before() {
        RestAssured.port = port;
        RestAssured.basePath = API_CONFERENCE;
    }

    @Given("une conférence ayant le nom {string}, le lien {string} et qui dure entre {string} et {string}")
    public void uneConférenceAvantLeNomLeLienEtQuiDureEntreEt(String name, String link, String startDate, String endDate) {
        this.name = name;
        this.link = link;
        this.sartDate = startDate;
        this.endDate = endDate;
    }

    @When("l utilisateur tente de l enregistrer")
    public void lUtilisateurTenteDeLEnregistrer() throws JsonProcessingException {
        conferenceJson = new ConferenceJson(name, link, sartDate, endDate, pricingRanges);
        executePost("", conferenceJson);
    }

    @Then("la conférence est enregistée")
    public void laConférenceEstEnregistée() {
        final Integer savedConferenceId = response.then().extract().as(Integer.class);
        JpaConference jpaConference = jpaConferenceRepository.findById(savedConferenceId).orElse(null);
        Long pricingRangeCount = jpaPricingRangeRepository.count();
        JpaConference expectedJpaConference = new JpaConference(
              savedConferenceId,
              conferenceJson.name(),
              conferenceJson.link(),
              LocalDate.parse(conferenceJson.startDate(), DATE_TIME_FORMATTER),
              LocalDate.parse(conferenceJson.endDate(), DATE_TIME_FORMATTER)
        );

        assertThat(expectedJpaConference).usingRecursiveComparison().ignoringFields("pricingRanges").isEqualTo(jpaConference);
        assertThat(pricingRangeCount).isEqualTo(4);
    }

    @And("qu'elle a un système de tarifiaction early bird à {double} € avant {string}")
    public void quElleAUnSystèmeDeTarifiactionEarlyBirdÀ€Avant(double price, String endDate) {
        PricingRangeJson pricingRangeJson = new PricingRangeJson(null, endDate, price);
        pricingRanges.add(pricingRangeJson);
    }

    @And("qu'elle a un système de tarifiaction early bird à {double} € entre {string} et {string}")
    public void quElleAUnSystèmeDeTarifiactionEarlyBirdÀ€EntreEt(double price, String startDate, String endDate) {
        PricingRangeJson pricingRangeJson = new PricingRangeJson(startDate, endDate, price);
        pricingRanges.add(pricingRangeJson);
    }

    @And("qu'elle a un système de tarifiaction early bird à {double} € à partir du {string}")
    public void quElleAUnSystèmeDeTarifiactionEarlyBirdÀ€ÀPartirDu(double price, String startDate) {
        PricingRangeJson pricingRangeJson = new PricingRangeJson(startDate, null, price);
        pricingRanges.add(pricingRangeJson);
    }
}
