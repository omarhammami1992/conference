package com.soat.back.acceptance;

import com.soat.back.common.infrastructure.*;
import com.soat.back.conference.command.use_case.ConferenceParams;
import com.soat.back.conference.command.use_case.PriceAttendingDaysParams;
import com.soat.back.conference.command.use_case.PriceGroupParams;
import com.soat.back.conference.command.use_case.PriceRangeParams;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.RestAssured;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@DirtiesContext
@CucumberContextConfiguration
@ActiveProfiles("AcceptanceTest")
public class ConferenceSteps extends AcceptanceTest {

    private List<PriceAttendingDaysParams> priceAttendingDaysParams = new ArrayList<>();
    private List<PriceRangeParams> priceRangeParams = new ArrayList<>();

    private static final String API_CONFERENCE = "/conference";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private ConferenceParams conferenceParams;

    @Autowired
    private JpaConferenceRepository jpaConferenceRepository;
    private String name;
    private String link;
    private LocalDate startDate;
    private LocalDate endDate;
    private float price;
    private PriceGroupParams priceGroupParams;
    private String city;
    private String country;

    @Before
    public void before() {
        RestAssured.port = port;
        RestAssured.basePath = API_CONFERENCE;
        priceGroupParams = null;
        priceRangeParams = new ArrayList<>();
        priceAttendingDaysParams = new ArrayList<>();
    }

    @Given("une conférence ayant le nom {string}, le lien {string} et qui dure entre le {string} et le {string}")
    public void uneConférenceAvantLeNomLeLienEtQuiDureEntreEt(String name, String link, String startDate, String endDate) {
        this.name = name;
        this.link = link;
        this.startDate = LocalDate.parse(startDate, DATE_TIME_FORMATTER);
        this.endDate = LocalDate.parse(endDate, DATE_TIME_FORMATTER);
    }

    @When("l utilisateur tente de l enregistrer")
    public void lUtilisateurTenteDeLEnregistrer() {
        conferenceParams = new ConferenceParams(name, link, startDate, endDate, price, priceRangeParams, priceGroupParams, priceAttendingDaysParams, city, country);
        executePost("", conferenceParams);
    }

    @And("qu'elle a un système de tarification early bird à {float} € avant le {string}")
    public void quElleAUnSystèmeDetarificationEarlyBirdÀ€Avant(float price, String endDate) {
        LocalDate end = LocalDate.parse(endDate, DATE_TIME_FORMATTER);
        var priceRange = new PriceRangeParams(price, null, end);
        priceRangeParams.add(priceRange);
    }

    @Then("la conférence est enregistée avec le prix {float} € et les intervalles de réduction early bird à {string} en {string}")
    @Transactional
    public void laConférenceEstEnregistéeAvecLePrix€EtLesIntervallesDeRéductionEarlyBird(float defaultPrice, String city, String country, DataTable dataTable) {
        final Integer savedConferenceId = response.then().extract().as(Integer.class);
        JpaConference jpaConference = jpaConferenceRepository.findById(savedConferenceId).orElse(null);
        JpaConference expectedJpaConference = new JpaConference(
                savedConferenceId,
                conferenceParams.name(),
                conferenceParams.link(),
                defaultPrice,
                conferenceParams.startDate(),
                conferenceParams.endDate(),
                city,
                country
        );

        assertThat(jpaConference).usingRecursiveComparison()
                .ignoringFields("priceRanges", "priceGroup", "priceAttendingDays")
                .isEqualTo(expectedJpaConference);

        List<JpaPriceRange> expectedJpaPriceRanges = dataTableTransformEntries(dataTable, this::buildJpaPriceRange);

        assert jpaConference != null;
        assertThat(jpaConference.getPriceRanges())
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "conference")
                .containsExactlyInAnyOrder(expectedJpaPriceRanges.toArray(JpaPriceRange[]::new));
    }

    @And("qu'elle a un système de tarification early bird à {float} € entre le {string} et le {string}")
    public void quElleAUnSystèmeDetarificationEarlyBirdÀ€EntreEt(float price, String startDate, String endDate) {
        var priceRange = new PriceRangeParams(price, LocalDate.parse(startDate, DATE_TIME_FORMATTER), LocalDate.parse(endDate, DATE_TIME_FORMATTER));
        priceRangeParams.add(priceRange);
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
        this.priceGroupParams = new PriceGroupParams(ticketPrice, participantThreshold);
    }

    @Then("la conférence est enregistée avec le prix {int} € et un prix réduit de {int} € à partir de {int} participants à {string} en {string}")
    public void laConférenceEstEnregistéeAvecLePrix€EtUnPrixRéduitDe€ÀPartirDeParticipantsÀEn(float price, int groupPrice, int threshold, String city, String country) {
        final Integer savedConferenceId = response.then().extract().as(Integer.class);
        JpaConference jpaConference = jpaConferenceRepository.findById(savedConferenceId).orElse(null);
        JpaConference expectedJpaConference = new JpaConference(
                savedConferenceId,
                name,
                link,
                price,
                startDate,
                endDate,
                city,
                country
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
        var priceAttendingDays = new PriceAttendingDaysParams(price, attendingDays);
        priceAttendingDaysParams.add(priceAttendingDays);

    }

    @Then("la conférence est enregistée avec le prix {int} € et les prix réduits par jour de présence à {string} en {string}")
    @Transactional
    public void laConférenceEstEnregistéeAvecLePrix€EtLesPrixRéduitsParJourDePrésenceÀEn(float price, String city, String country, DataTable dataTable) {
        assertThat(response.statusCode()).isEqualTo(201);
        final Integer savedConferenceId = response.then().extract().as(Integer.class);
        JpaConference jpaConference = jpaConferenceRepository.findById(savedConferenceId).orElse(null);
        JpaConference expectedJpaConference = new JpaConference(
                savedConferenceId,
                conferenceParams.name(),
                conferenceParams.link(),
                price,
                conferenceParams.startDate(),
                conferenceParams.endDate(),
                city,
                country
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
                Integer.parseInt(entry.get("id")),
                entry.get("name"),
                entry.get("link"),
                Float.valueOf(entry.get("price")),
                LocalDate.parse(entry.get("startDate"), DATE_TIME_FORMATTER),
                LocalDate.parse(entry.get("endDate"), DATE_TIME_FORMATTER),
                entry.get("city"),
                entry.get("country"));
    }

    private com.soat.back.conference.query.application.ConferenceJson buildConferencesDto(Map<String, String> entry) {
        return new com.soat.back.conference.query.application.ConferenceJson(
                Integer.parseInt(entry.get("id")),
                entry.get("name"),
                entry.get("link"),
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

    @Then("la conférence récupérée devrait contenir l id {int}, le nom {string}, le lien {string}, ayant le prix {int} et qui dure entre le {string} et le {string} et qui aura lieu à {string} \\({string}) {string}")
    public void laConférenceRécupéréeDevraitContenirLeNomLeLienAyantLePrixEtQuiDureEntreLeEtLe(int id, String name, String link, float price, String startDate, String endDate, String city, String country, String isOnLineValueAsString) {
        final boolean isOnLine = !isOnLineValueAsString.equals("en présentielle");
        var expectedConference = new com.soat.back.conference.query.application.ConferenceJson(id, name, link, LocalDate.parse(startDate, DATE_TIME_FORMATTER), LocalDate.parse(endDate, DATE_TIME_FORMATTER), price, isOnLine, city, country);

        var conference = response.then()
                .extract()
                .as(com.soat.back.conference.query.application.ConferenceJson.class);

        assertThat(conference).usingRecursiveComparison()
                .ignoringFields("priceGroup", "priceRanges", "attendingDays")
                .isEqualTo(expectedConference);
    }

    @Then("la conférence n'existe pas")
    public void laConférenceNExistePas() {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Given("une conférence ayant le nom {string}, le lien {string} et qui dure entre le {string} et le {string} et qui aura lieu à {string} en {string}")
    public void uneConférenceAyantLeNomLeLienEtQuiDureEntreLeEtLeEtQuiAuraLieuÀEn(String name, String link, String startDate, String endDate, String city, String country) {
        this.name = name;
        this.link = link;
        this.startDate = LocalDate.parse(startDate, DATE_TIME_FORMATTER);
        this.endDate = LocalDate.parse(endDate, DATE_TIME_FORMATTER);
        this.city = city;
        this.country = country;
    }

    @Then("la conférence est enregistée avec le prix {float} €")
    public void laConférenceEstEnregistéeAvecLePrix€(float defaultPrice) {
        final Integer savedConferenceId = response.then().extract().as(Integer.class);
        JpaConference jpaConference = jpaConferenceRepository.findById(savedConferenceId).orElse(null);
        ;
        assertThat(jpaConference).isNotNull();
        assertThat(jpaConference.getPrice()).isEqualTo(defaultPrice);
    }
}
