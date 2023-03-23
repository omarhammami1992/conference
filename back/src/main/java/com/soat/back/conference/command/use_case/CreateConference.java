package com.soat.back.conference.command.use_case;

import com.soat.back.conference.command.domain.Conference;
import com.soat.back.conference.command.domain.DateInterval;
import com.soat.back.conference.command.domain.InvalidAttendingDaysException;
import com.soat.back.conference.command.domain.InvalidIntervalException;
import com.soat.back.conference.command.domain.InvalidPricesException;
import com.soat.back.conference.command.domain.InvalidThresholdException;
import com.soat.back.conference.command.domain.PriceAttendingDay;
import com.soat.back.conference.command.domain.PriceAttendingDays;
import com.soat.back.conference.command.domain.PriceGroup;
import com.soat.back.conference.command.domain.PriceRange;
import com.soat.back.conference.command.domain.PriceRanges;

public class CreateConference {

    private final ConferencePort conferencePort;

    public CreateConference(ConferencePort conferencePort) {
        this.conferencePort = conferencePort;
    }

    public Integer execute(ConferenceParams conferenceParams) throws InvalidIntervalException, InvalidPricesException, InvalidThresholdException, InvalidAttendingDaysException {
        final PriceRanges priceRanges = buildPriceRanges(conferenceParams);
        final PriceGroup priceGroup = buildPriceGroup(conferenceParams.priceGroupParams());
        final PriceAttendingDays priceAttendingDays = buildPriceAttendingDays(conferenceParams);

        Conference conference;

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

    private static boolean hasPriceGroups(PriceRanges priceRanges, PriceAttendingDays priceAttendingDays) {
        return priceRanges.isEmpty() && priceAttendingDays.isEmpty();
    }

    private PriceAttendingDays buildPriceAttendingDays(ConferenceParams conferenceParams) throws InvalidAttendingDaysException {
        return PriceAttendingDays.create(conferenceParams.priceAttendingDaysParams().stream()
                .map(priceAttendingDay -> new PriceAttendingDay(priceAttendingDay.price(), priceAttendingDay.attendingDays()))
                .toList());
    }

    private static PriceGroup buildPriceGroup(PriceGroupParams priceGroupParams) {
        PriceGroup priceGroup = null;
        if (priceGroupParams != null) {
            priceGroup = new PriceGroup(priceGroupParams.priceGroup(), priceGroupParams.participantsThreshold());
        }
        return priceGroup;
    }

    private static PriceRanges buildPriceRanges(ConferenceParams conferenceParams) throws InvalidPricesException {
        return PriceRanges.create(conferenceParams.priceRanges().stream()
                .map(priceRange -> new PriceRange(priceRange.price(), new DateInterval(priceRange.startDate(), priceRange.endDate())))
                .toList());
    }

}
