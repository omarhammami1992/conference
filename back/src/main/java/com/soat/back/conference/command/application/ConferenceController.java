package com.soat.back.conference.command.application;

import com.soat.back.conference.command.domain.ConferenceParams;
import com.soat.back.conference.command.domain.CreateConference;
import com.soat.back.conference.command.domain.InvalidIntervalException;
import com.soat.back.conference.command.domain.PriceRangeParams;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.util.Optional.ofNullable;

@RestController
@RequestMapping("/conference")
public class ConferenceController {

    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private final CreateConference createConference;

    public ConferenceController(CreateConference createConference) {
        this.createConference = createConference;
    }

    @PostMapping("")
    public ResponseEntity<Integer> save(@RequestBody ConferenceJson conferenceJson) throws InvalidIntervalException {
        ConferenceParams conferenceParams = toConferenceParams(conferenceJson);
        Integer id = createConference.execute(conferenceParams);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    private ConferenceParams toConferenceParams(ConferenceJson conferenceJson) {
        List<PriceRangeParams> priceRangeParams = conferenceJson.priceRanges().stream()
                .map(this::toPriceRangeParams)
                .toList();

        return new ConferenceParams(
                conferenceJson.name(),
                conferenceJson.link(),
                LocalDate.parse(conferenceJson.startDate(), DATE_TIME_FORMATTER),
                LocalDate.parse(conferenceJson.endDate(), DATE_TIME_FORMATTER),
                conferenceJson.price(),
                priceRangeParams,
                ofNullable(conferenceJson.priceGroup()).map(PriceGroupJson::price).orElse(null),
                ofNullable(conferenceJson.priceGroup()).map(PriceGroupJson::participantsThreshold).orElse(null)
        );
    }

    private PriceRangeParams toPriceRangeParams(PriceRangeJson priceRangeJson) {
        return new PriceRangeParams(
                priceRangeJson.price(),
                toDate(priceRangeJson.startDate()),
                toDate(priceRangeJson.endDate())
        );
    }

    private LocalDate toDate(String priceRangeJson) {
        return ofNullable(priceRangeJson).map(startDate -> LocalDate.parse(startDate, DATE_TIME_FORMATTER)).orElse(null);
    }
}
