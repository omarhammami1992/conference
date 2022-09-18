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
import com.soat.back.conference.command.domain.Conference;
import com.soat.back.conference.command.domain.CreateConference;
import com.soat.back.conference.command.domain.PricingRange;

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
        Conference buildConference = convertToConference(conferenceJson);
        Integer id = createConference.execute(buildConference);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    private Conference convertToConference(ConferenceJson conferenceJson) {
        List<PricingRange> pricingRanges = conferenceJson.pricingRanges()
              .stream().map(this::convertToPricingRange)
              .toList();

        return new Conference(conferenceJson.name(),
              conferenceJson.link(),
              LocalDate.parse(conferenceJson.startDate(), DATE_TIME_FORMATTER),
              LocalDate.parse(conferenceJson.endDate(), DATE_TIME_FORMATTER),
              pricingRanges
        );
    }

    private PricingRange convertToPricingRange(PricingRangeJson pricingRangeJson) {
        LocalDate startDate = ofNullable(pricingRangeJson.startDate())
              .map(date -> LocalDate.parse(date, DATE_TIME_FORMATTER))
              .orElse(null);

        LocalDate endDate = ofNullable(pricingRangeJson.endDate())
              .map(date -> LocalDate.parse(date, DATE_TIME_FORMATTER))
              .orElse(null);

        return new PricingRange(
              startDate,
              endDate,
              pricingRangeJson.price()
        );
    }
}
