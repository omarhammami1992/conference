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

    @Given("une conf??rence ayant le nom {string}, le lien {string} et qui dure entre le {string} et le {string}")
    public void uneConf??renceAvantLeNomLeLienEtQuiDureEntreEt(String name, String link, String startDate, String endDate) {
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

    @And("qu'elle a un syst??me de tarification early bird ?? {float} ??? avant le {string}")
    public void quElleAUnSyst??meDetarificationEarlyBird?????Avant(float price, String endDate) {
        PriceRangeJson priceRangeJson = new PriceRangeJson(price, null, endDate);
        priceRangeJsons.add(priceRangeJson);
    }

    @Then("la conf??rence est enregist??e avec le prix {float} ??? et  les intervalles de r??duction early bird")
    @Transactional
    public void laConf??renceEstEnregist??eAvecLePrix???EtLesIntervallesDeR??ductionEarlyBird(float defaultPrice, DataTable dataTable) {
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

    @And("qu'elle a un syst??me de tarification early bird ?? {float} ??? entre le {string} et le {string}")
    public void quElleAUnSyst??meDetarificationEarlyBird?????EntreEt(float price, String startDate, String endDate) {
        PriceRangeJson priceRangeJson = new PriceRangeJson(price, startDate, endDate);
        priceRangeJsons.add(priceRangeJson);
    }

    @And("qu'elle a une tarification pleine ?? {float} ???")
    public void quElleAUneTarificationPleine???????PartirDu(float price) {
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

    @And("qu'elle a un syst??me de tarification de groupe ?? {float} ??? par personne lorsqu on r??serve ?? partir de {int} billets")
    public void quElleAUnSyst??meDeTarificationDeGroupe?????ParPersonneLorsquOnR??serve??PartirDeBillets(float ticketPrice, int participantThreshold) {
        this.priceGroupJson = new PriceGroupJson(ticketPrice, participantThreshold);
    }

    @Then("la conf??rence est enregist??e avec le prix {float} ??? et un prix r??duit de {float} ??? ?? partir de {int} participants")
    public void laConf??renceEstEnregist??eAvecLePrix???EtUnPrixR??duitDe?????PartirDeParticipants(float price, float groupPrice, int threshold) {
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

    @And("qu'elle a un syst??me de tarification par journ??e de pr??sence ?? {float} ??? les {float} jours")
    public void quElleAUnSyst??meDeTarificationParJourn??eDePr??sence?????LesJours(float price, float attendingDays) {
        PriceAttendingDaysJson priceAttendingDaysJson = new PriceAttendingDaysJson(price, attendingDays);
        priceAttendingDaysJsons.add(priceAttendingDaysJson);

    }

    @Then("la conf??rence est enregist??e avec le prix {float} ??? et les prix r??duits par jour de pr??sence")
    @Transactional
    public void laConf??renceEstEnregist??eAvecLePrix???EtLesPrixR??duitsParJourDePr??sence(float price, DataTable dataTable) {
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

    @Given("une liste de conf??rences enregistr??es")
    public void uneListeDeConf??rencesEnregistr??es(DataTable dataTable) {
        var conferences = dataTableTransformEntries(dataTable, this::buildJpaConferences);
        jpaConferenceRepository.saveAll(conferences);
    }

    @When("l'utilisateur consulte la liste de conferences")
    public void lUtilisateurConsulteLaListeDeConferences() {
        executeGet("");
    }

    @Then("les conf??rences ?? venir s'affichent")
    public void lesConf??rences??VenirSAffichent(DataTable dataTable) {
        var expectedConferences = dataTableTransformEntries(dataTable, this::buildConferencesDto);

        var conferences = response.then()
                .extract()
                .as(com.soat.back.conference.query.application.ConferenceJson[].class);

        assertThat(Arrays.stream(conferences).toList())
                .containsExactlyInAnyOrder(expectedConferences.toArray(com.soat.back.conference.query.application.ConferenceJson[]::new));

    }
}
