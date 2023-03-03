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

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.soat.back.common.infrastructure.JpaConference;
import com.soat.back.common.infrastructure.JpaConferenceRepository;
import com.soat.back.common.infrastructure.JpaPriceAttendingDay;
import com.soat.back.common.infrastructure.JpaPriceGroup;
import com.soat.back.common.infrastructure.JpaPriceRange;
import com.soat.back.conference.command.application.ConferenceJson;
import com.soat.back.conference.command.application.PriceAttendingDaysJson;
import com.soat.back.conference.command.application.PriceGroupJson;
import com.soat.back.conference.command.application.PriceRangeJson;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@DirtiesContext
@CucumberContextConfiguration
@ActiveProfiles("AcceptanceTest")
public class ConferenceSteps extends AcceptanceTest {

    private static final List<PriceAttendingDaysJson> priceAttendingDaysJsons = new ArrayList<>();
    private static List<PriceRangeJson> priceRangeJsons = new ArrayList<>();

    private static final String API_CONFERENCE = "/conference";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private ConferenceJson conferenceJson;

    @Autowired
    private JpaConferenceRepository jpaConferenceRepository;
    private String name;
    private String link;
    private LocalDate startDate;
    private LocalDate endDate;
    private float price;
    private PriceGroupJson priceGroupJson;

    @Before
    public void before() {
        RestAssured.port = port;
        RestAssured.basePath = API_CONFERENCE;
        priceGroupJson = null;
        priceRangeJsons = new ArrayList<>();
    }

    @Given("une conférence ayant le nom {string}, le lien {string} et qui dure entre le {string} et le {string}")
    public void uneConférenceAvantLeNomLeLienEtQuiDureEntreEt(String name, String link, String startDate, String endDate) {
        this.name = name;
        this.link = link;
        this.startDate = LocalDate.parse(startDate, DATE_TIME_FORMATTER);
        this.endDate = LocalDate.parse(endDate, DATE_TIME_FORMATTER);
    }

    @When("l utilisateur tente de l enregistrer")
    public void lUtilisateurTenteDeLEnregistrer() throws JsonProcessingException {
        conferenceJson = new ConferenceJson(name, link, startDate, endDate, price, priceRangeJsons, priceGroupJson, priceAttendingDaysJsons);
        executePost("", conferenceJson);
    }

    @And("qu'elle a un système de tarification early bird à {float} € avant le {string}")
    public void quElleAUnSystèmeDetarificationEarlyBirdÀ€Avant(float price, String endDate) {
        PriceRangeJson priceRangeJson = new PriceRangeJson(price, null, endDate);
        priceRangeJsons.add(priceRangeJson);
    }

    @Then("la conférence est enregistée avec le prix {float} € et  les intervalles de réduction early bird")
    @Transactional
    public void laConférenceEstEnregistéeAvecLePrix€EtLesIntervallesDeRéductionEarlyBird(float defaultPrice, DataTable dataTable) {
        final Integer savedConferenceId = response.then().extract().as(Integer.class);
        JpaConference jpaConference = jpaConferenceRepository.findById(savedConferenceId).orElse(null);
        JpaConference expectedJpaConference = new JpaConference(
                savedConferenceId,
                conferenceJson.name(),
                conferenceJson.link(),
                defaultPrice,
                conferenceJson.startDate(),
                conferenceJson.endDate()
        );

        assertThat(jpaConference).usingRecursiveComparison()
                .ignoringFields("priceRanges", "priceGroup", "priceAttendingDays")
                .isEqualTo(expectedJpaConference);

        List<JpaPriceRange> jpaPriceRanges = dataTableTransformEntries(dataTable, this::buildJpaPriceRange);

        assert jpaConference != null;
        assertThat(jpaConference.getPriceRanges())
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "conference")
                .containsExactlyInAnyOrder(jpaPriceRanges.toArray(JpaPriceRange[]::new));
    }

    @And("qu'elle a un système de tarification early bird à {float} € entre le {string} et le {string}")
    public void quElleAUnSystèmeDetarificationEarlyBirdÀ€EntreEt(float price, String startDate, String endDate) {
        PriceRangeJson priceRangeJson = new PriceRangeJson(price, startDate, endDate);
        priceRangeJsons.add(priceRangeJson);
    }

    @And("qu'elle a une tarification pleine à {float} €")
    public void quElleAUneTarificationPleineÀ€ÀPartirDu(float price) {
        this.price = price;
    }

    private static <T> List<T> dataTableTransformEntries(DataTable dataTable, Function<Map<String, String>, T> transformFunction) {
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

    @And("qu'elle a un système de tarification de groupe à {float} € par personne lorsqu on réserve à partir de {int} billets")
    public void quElleAUnSystèmeDeTarificationDeGroupeÀ€ParPersonneLorsquOnRéserveÀPartirDeBillets(float ticketPrice, int participantThreshold) {
        this.priceGroupJson = new PriceGroupJson(ticketPrice, participantThreshold);
    }

    @Then("la conférence est enregistée avec le prix {float} € et un prix réduit de {float} € à partir de {int} participants")
    public void laConférenceEstEnregistéeAvecLePrix€EtUnPrixRéduitDe€ÀPartirDeParticipants(float price, float groupPrice, int threshold) {
        final Integer savedConferenceId = response.then().extract().as(Integer.class);
        JpaConference jpaConference = jpaConferenceRepository.findById(savedConferenceId).orElse(null);
        JpaConference expectedJpaConference = new JpaConference(
                savedConferenceId,
                conferenceJson.name(),
                conferenceJson.link(),
                price,
                conferenceJson.startDate(),
                conferenceJson.endDate()
        );

        assertThat(jpaConference).isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("priceRanges", "priceGroup", "priceAttendingDays")
                .isEqualTo(expectedJpaConference);

        JpaPriceGroup expectedPriceGroup = new JpaPriceGroup(groupPrice, threshold);
        assertThat(jpaConference.getGroupPrice()).usingRecursiveComparison().ignoringFields("id", "conference")
                .isEqualTo(expectedPriceGroup);

    }

    @And("qu'elle a un système de tarification par journée de présence à {float} € les {float} jours")
    public void quElleAUnSystèmeDeTarificationParJournéeDePrésenceÀ€LesJours(float price, float attendingDays) {
        PriceAttendingDaysJson priceAttendingDaysJson = new PriceAttendingDaysJson(price, attendingDays);
        priceAttendingDaysJsons.add(priceAttendingDaysJson);

    }

    @Then("la conférence est enregistée avec le prix {float} € et les prix réduits par jour de présence")
    @Transactional
    public void laConférenceEstEnregistéeAvecLePrix€EtLesPrixRéduitsParJourDePrésence(float price, DataTable dataTable) {
        final Integer savedConferenceId = response.then().extract().as(Integer.class);
        JpaConference jpaConference = jpaConferenceRepository.findById(savedConferenceId).orElse(null);
        JpaConference expectedJpaConference = new JpaConference(
                savedConferenceId,
                conferenceJson.name(),
                conferenceJson.link(),
                price,
                conferenceJson.startDate(),
                conferenceJson.endDate()
        );

        assertThat(jpaConference).usingRecursiveComparison()
                .ignoringFields("priceRanges", "priceGroup", "priceAttendingDays")
                .isEqualTo(expectedJpaConference);

        var attendingDays = dataTableTransformEntries(dataTable, this::buildJpaPriceAttendingDay);
        assertThat(jpaConference.getPriceAttendingDays())
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "conference")
                .containsExactlyInAnyOrder(attendingDays.toArray(JpaPriceAttendingDay[]::new));
    }

    private JpaPriceAttendingDay buildJpaPriceAttendingDay(Map<String, String> entry) {
        return new JpaPriceAttendingDay(
                Float.valueOf(entry.get("price")),
                Float.valueOf(entry.get("attendingDays")));
    }

    private JpaConference buildJpaConferences(Map<String, String> entry) {
        return new JpaConference(
                entry.get("name"),
                entry.get("link"),
                Float.valueOf(entry.get("price")),
                LocalDate.parse(entry.get("startDate"), DATE_TIME_FORMATTER),
                LocalDate.parse(entry.get("endDate"), DATE_TIME_FORMATTER));
    }

    private com.soat.back.conference.query.application.ConferenceJson buildConferencesDto(Map<String, String> entry) {
        return new com.soat.back.conference.query.application.ConferenceJson(
                Integer.parseInt(entry.get("id")),
                entry.get("name"),
                LocalDate.parse(entry.get("startDate"), DATE_TIME_FORMATTER),
                LocalDate.parse(entry.get("endDate"), DATE_TIME_FORMATTER),
                Float.valueOf(entry.get("fullPrice")),
                Boolean.parseBoolean(entry.get("isOnline")),
                entry.get("city"),
                entry.get("country"));
    }

    @Given("une liste de conférences enregistrées")
    public void uneListeDeConférencesEnregistrées(DataTable dataTable) {
        var conferences = dataTableTransformEntries(dataTable, this::buildJpaConferences);
        jpaConferenceRepository.saveAll(conferences);
    }

    @When("l'utilisateur consulte la liste de conferences")
    public void lUtilisateurConsulteLaListeDeConferences() {
        executeGet("");
    }

    @Then("les conférences à venir s'affichent")
    public void lesConférencesÀVenirSAffichent(DataTable dataTable) {
        var expectedConferences = dataTableTransformEntries(dataTable, this::buildConferencesDto);

        var conferences = response.then()
                .extract()
                .as(com.soat.back.conference.query.application.ConferenceJson[].class);

        assertThat(Arrays.stream(conferences).toList())
                .containsExactlyInAnyOrder(expectedConferences.toArray(com.soat.back.conference.query.application.ConferenceJson[]::new));

    }

    @When("l'utilisateur consulte le détail de la conférence {int}")
    public void lUtilisateurConsulteLeDétailDeLaConférence(int id) {
        executeGet("/" + id);
    }

    @Then("la conférence récupérée devrait contenir le nom {string}, le lien {string}, ayant le prix {float} et qui dure entre le {string} et le {string}")
    public void laConférenceRécupéréeDevraitContenirLeNomLeLienAyantLePrixEtQuiDureEntreLeEtLe(String name, String link, float price, String startDate, String endDate) {
        var expectedConference = new ConferenceJson(name, link, LocalDate.parse(startDate, DATE_TIME_FORMATTER), LocalDate.parse(endDate, DATE_TIME_FORMATTER),price,null,null,null);

        var conference = response.then()
                .extract()
                .as(ConferenceJson.class);

        assertThat(conference).usingRecursiveComparison().ignoringFields("priceGroup", "priceRanges", "attendingDays").isEqualTo(expectedConference);
    }
}
