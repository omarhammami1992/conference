package com.soat.back.acceptance;

import io.cucumber.datatable.DataTable;
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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.soat.back.common.infrastructure.JpaConference;
import com.soat.back.common.infrastructure.JpaConferenceRepository;
import com.soat.back.common.infrastructure.JpaPriceRange;
import com.soat.back.conference.command.application.ConferenceJson;
import com.soat.back.conference.command.application.PriceRangeJson;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@DirtiesContext
@CucumberContextConfiguration
@ActiveProfiles("AcceptanceTest")
public class ConferenceSteps extends AcceptanceTest {

   private static final List<PriceRangeJson> PRICE_RANGE_JSONS = new ArrayList<>();

   private static final String API_CONFERENCE = "/conference";
   private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
   private ConferenceJson conferenceJson;

   @Autowired
   private JpaConferenceRepository jpaConferenceRepository;
   private String name;
   private String link;
   private String startDate;
   private String endDate;

   @Before
   public void before() {
      RestAssured.port = port;
      RestAssured.basePath = API_CONFERENCE;
   }

   @Given("une conférence ayant le nom {string}, le lien {string} et qui dure entre le {string} et le {string}")
   public void uneConférenceAvantLeNomLeLienEtQuiDureEntreEt(String name, String link, String startDate, String endDate) {
      this.name = name;
      this.link = link;
      this.startDate = startDate;
      this.endDate = endDate;
   }

   @When("l utilisateur tente de l enregistrer")
   public void lUtilisateurTenteDeLEnregistrer() throws JsonProcessingException {
      conferenceJson = new ConferenceJson(name, link, startDate, endDate, PRICE_RANGE_JSONS);
      executePost("", conferenceJson);
   }


   @Then("la conférence est enregistée")
   public void laConférenceEstEnregistée(DataTable dataTable) {
      final Integer savedConferenceId = response.then().extract().as(Integer.class);
      JpaConference jpaConference = jpaConferenceRepository.findById(savedConferenceId).orElse(null);
      JpaConference expectedJpaConference = new JpaConference(
            savedConferenceId,
            conferenceJson.name(),
            conferenceJson.link(),
            LocalDate.parse(conferenceJson.startDate(), DATE_TIME_FORMATTER),
            LocalDate.parse(conferenceJson.endDate(), DATE_TIME_FORMATTER));

      assertThat(expectedJpaConference).usingRecursiveComparison().ignoringFields("priceRanges").isEqualTo(jpaConference);

      List<JpaPriceRange> jpaPriceRanges = dataTableTransformEntries(dataTable, this::buildJpaPriceRange);

      assertThat(expectedJpaConference.getPriceRanges())
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
            .containsExactlyInAnyOrder(jpaPriceRanges.toArray(JpaPriceRange[]::new));
   }

   @And("qu'elle a un système de tarification early bird à {float} € avant le {string}")
   public void quElleAUnSystèmeDetarificationEarlyBirdÀ€Avant(float price, String endDate) {
      PriceRangeJson priceRangeJson = new PriceRangeJson(price, null, endDate);
      PRICE_RANGE_JSONS.add(priceRangeJson);
   }

   @And("qu'elle a un système de tarification early bird à {float} € entre le {string} et le {string}")
   public void quElleAUnSystèmeDetarificationEarlyBirdÀ€EntreEt(float price, String startDate, String endDate) {
      PriceRangeJson priceRangeJson = new PriceRangeJson(price, startDate, endDate);
      PRICE_RANGE_JSONS.add(priceRangeJson);
   }

   @And("qu'elle a une tarification pleine à {float} € à partir du {string}")
   public void quElleAUneTarificationPleineÀ€ÀPartirDu(float price, String startDate) {
      PriceRangeJson priceRangeJson = new PriceRangeJson(price, startDate, null);
      PRICE_RANGE_JSONS.add(priceRangeJson);
   }

   public static <T> List<T> dataTableTransformEntries(DataTable dataTable, Function<Map<String, String>, T> transformFunction) {
      final List<T> transformResults = new ArrayList<>();
      final List<Map<String, String>> dataTableEntries = dataTable.asMaps(String.class, String.class);
      dataTableEntries.forEach(mapEntry -> {
         transformResults.add(transformFunction.apply(mapEntry));
      });
      return transformResults;
   }

   private JpaPriceRange buildJpaPriceRange(Map<String, String> entry) {
      var startDate = entry.get("startDate").equals("null") ? null : LocalDate.parse(entry.get("startDate"), DATE_TIME_FORMATTER);
      var endDate = entry.get("endDate").equals("null") ? null : LocalDate.parse(entry.get("endDate"), DATE_TIME_FORMATTER);
      return new JpaPriceRange(
            Float.valueOf(entry.get("price")),
            startDate,
            endDate);
   }
}
