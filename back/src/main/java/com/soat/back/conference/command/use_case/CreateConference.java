package com.soat.back.conference.command.use_case;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

import com.soat.back.conference.command.domain.Conference;
import com.soat.back.conference.command.domain.DateInterval;
import com.soat.back.conference.command.domain.InvalidAttendingDaysException;
import com.soat.back.conference.command.domain.InvalidIntervalException;
import com.soat.back.conference.command.domain.InvalidPricesException;
import com.soat.back.conference.command.domain.InvalidThresholdException;
import com.soat.back.conference.command.domain.PriceAttendingDay;
import com.soat.back.conference.command.domain.PriceGroup;
import com.soat.back.conference.command.domain.PriceRange;

import static java.time.temporal.ChronoUnit.DAYS;

public class CreateConference {

    private final ConferencePort conferencePort;

    public CreateConference(ConferencePort conferencePort) {
        this.conferencePort = conferencePort;
    }

    public Integer execute(ConferenceParams conferenceParams) throws InvalidIntervalException, InvalidPricesException, InvalidThresholdException, InvalidAttendingDaysException {
        final List<PriceRange> priceRanges = buildPriceRanges(conferenceParams);
        final PriceGroup priceGroup = buildPriceGroup(conferenceParams.priceGroupParams());
        final List<PriceAttendingDay> priceAttendingDays = buildPriceAttendingDays(conferenceParams);

        Conference conference;
        if (checkMinimumPriceGreaterThanZero(priceRanges)) {
            throw new InvalidPricesException("Price range greater than zero");
        }

        if (checkUniquenessPriceInPriceRange(priceRanges)) {
            throw new InvalidPricesException("Non unique price range");
        }
        var conferencePeriodDay = DAYS.between(conferenceParams.startDate(), conferenceParams.endDate()) + 1.0;
        if (priceAttendingDays.stream().anyMatch(priceAttendingDay -> priceAttendingDay.attendingDay() == conferencePeriodDay)) {
            throw new InvalidAttendingDaysException(MessageFormat.format("Attending days should be lower than conference period {0} days", conferencePeriodDay));
        }

        if (hasPriceGroups(priceRanges, priceAttendingDays)) {
            conference = Conference.createPriceGroup(
                    conferenceParams.name(),
                    conferenceParams.link(),
                    conferenceParams.price(),
                    conferenceParams.startDate(),
                    conferenceParams.endDate(),
                    priceGroup,
                    conferenceParams.city(),
                    conferenceParams.country());
        } else if (priceAttendingDays.isEmpty()) {
            conference = Conference.createWithPriceRanges(
                    conferenceParams.name(),
                    conferenceParams.link(),
                    conferenceParams.price(),
                    conferenceParams.startDate(),
                    conferenceParams.endDate(),
                    priceRanges,
                    conferenceParams.city(),
                    conferenceParams.country());
        } else {
            conference = Conference.createWithPriceAttendingDays(
                    conferenceParams.name(),
                    conferenceParams.link(),
                    conferenceParams.price(),
                    conferenceParams.startDate(),
                    conferenceParams.endDate(),
                    priceAttendingDays,
                    conferenceParams.city(),
                    conferenceParams.country()
            );
        }

        return conferencePort.save(conference);
    }

    private static boolean hasPriceGroups(List<PriceRange> priceRanges, List<PriceAttendingDay> priceAttendingDays) {
        return priceRanges.isEmpty() && priceAttendingDays.isEmpty();
    }

    private static boolean checkMinimumPriceGreaterThanZero(List<PriceRange> priceRanges) {
        return priceRanges.stream()
                .mapToDouble(PriceRange::price)
                .anyMatch(price -> price < 0);
    }

    private static boolean checkUniquenessPriceInPriceRange(List<PriceRange> priceRanges) {
        return priceRanges
                .stream()
                .map(PriceRange::price)
                .collect(Collectors.toSet())
                .size() != priceRanges.stream().count();
    }

    private List<PriceAttendingDay> buildPriceAttendingDays(ConferenceParams conferenceParams) {
        return conferenceParams.priceAttendingDaysParams().stream()
                .map(priceAttendingDay -> new PriceAttendingDay(priceAttendingDay.price(), priceAttendingDay.attendingDays()))
                .toList();
    }

    private static PriceGroup buildPriceGroup(PriceGroupParams priceGroupParams) {
        PriceGroup priceGroup = null;
        if (priceGroupParams != null) {
            priceGroup = new PriceGroup(priceGroupParams.priceGroup(), priceGroupParams.participantsThreshold());
        }
        return priceGroup;
    }

    private static List<PriceRange> buildPriceRanges(ConferenceParams conferenceParams) {
        return conferenceParams.priceRanges().stream()
                .map(priceRange -> new PriceRange(priceRange.price(), new DateInterval(priceRange.startDate(), priceRange.endDate())))
                .toList();
    }

}
