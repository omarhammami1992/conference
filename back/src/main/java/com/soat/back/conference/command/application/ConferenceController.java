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
import com.soat.back.conference.command.domain.ConferenceParams;
import com.soat.back.conference.command.domain.CreateConference;
import com.soat.back.conference.command.domain.InvalidIntervalException;
import com.soat.back.conference.command.domain.InvalidPricesException;
import com.soat.back.conference.command.domain.InvalidThresholdException;
import com.soat.back.conference.command.domain.PriceAttendingDaysParams;
import com.soat.back.conference.command.domain.PriceGroupParams;
import com.soat.back.conference.command.domain.PriceRangeParams;

@RestController
@RequestMapping("/conference")
public class ConferenceController {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private final CreateConference createConference;

    public ConferenceController(CreateConference createConference) {
        this.createConference = createConference;
    }

    @PostMapping
    public ResponseEntity<Integer> save(@RequestBody ConferenceJson conferenceJson) throws InvalidIntervalException, InvalidPricesException, InvalidThresholdException {
        ConferenceParams conferenceParams = toConferenceParams(conferenceJson);
        Integer id = createConference.execute(conferenceParams);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    //TODO: move to utility class and add UTs
    private ConferenceParams toConferenceParams(ConferenceJson conferenceJson) {
        List<PriceRangeParams> priceRangeParams = conferenceJson.priceRanges().stream()
                .map(this::toPriceRangeParams)
                .toList();
        var priceGroupParams = toPriceGroupParams(conferenceJson);
        List<PriceAttendingDaysParams> priceAttendingDaysParams = conferenceJson.attendingDays().stream()
              .map(this::toPriceAttendingDaysParams)
              .toList();

        return new ConferenceParams(
              conferenceJson.name(),
              conferenceJson.link(),
              LocalDate.parse(conferenceJson.startDate(), DATE_TIME_FORMATTER),
              LocalDate.parse(conferenceJson.endDate(), DATE_TIME_FORMATTER),
              conferenceJson.price(),
              priceRangeParams,
              priceGroupParams,
              priceAttendingDaysParams
        );
    }

    private static PriceGroupParams toPriceGroupParams(ConferenceJson conferenceJson) {
        PriceGroupParams priceGroupParams = null;
        if (conferenceJson.priceGroup() != null) {
            priceGroupParams = new PriceGroupParams(conferenceJson.priceGroup().price(), conferenceJson.priceGroup().participantsThreshold() );
        }
        return priceGroupParams;
    }

    private PriceRangeParams toPriceRangeParams(PriceRangeJson priceRangeJson) {
        return new PriceRangeParams(
                priceRangeJson.price(),
                toLocalDate(priceRangeJson.startDate()),
                toLocalDate(priceRangeJson.endDate())
        );
    }

    private PriceAttendingDaysParams toPriceAttendingDaysParams(PriceAttendingDaysJson priceAttendingDaysJson) {
        return new PriceAttendingDaysParams(
              priceAttendingDaysJson.price(),
              priceAttendingDaysJson.attendingDays()
        );
    }

    private LocalDate toLocalDate(String dateValue) {
        return ofNullable(dateValue)
              .map(startDate -> LocalDate.parse(startDate, DATE_TIME_FORMATTER))
              .orElse(null);
    }
}
