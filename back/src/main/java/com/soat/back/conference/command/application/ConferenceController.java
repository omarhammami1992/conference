package com.soat.back.conference.command.application;

import static java.util.Optional.ofNullable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.soat.back.conference.command.domain.CreateConference;
import com.soat.back.conference.command.domain.DateInterval;
import com.soat.back.conference.command.domain.PriceRange;

@RestController
@RequestMapping("/conference")
public class ConferenceController{

    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private final CreateConference createConference;

    public ConferenceController(CreateConference createConference) {
        this.createConference = createConference;
    }

    @PostMapping("")
    public ResponseEntity<Integer> save(@RequestBody ConferenceJson conferenceJson) {
        ConferenceParams conferenceParams = convertToConferenceParams(conferenceJson);
        Integer id = createConference.execute(conferenceParams);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    private ConferenceParams convertToConferenceParams(ConferenceJson conferenceJson) {
        List<PriceRangeParams> priceRanges = conferenceJson.priceRangeJsons().stream()
              .map(this::toPriceRangeParams)
              .toList();
        return new ConferenceParams(
              conferenceJson.name(),
              conferenceJson.link(),
              LocalDate.parse(conferenceJson.startDate(), DATE_TIME_FORMATTER),
              LocalDate.parse(conferenceJson.endDate(), DATE_TIME_FORMATTER),
              priceRanges
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
