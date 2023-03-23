package com.soat.back.conference.command.use_case;

import com.soat.back.conference.command.domain.*;

import java.util.List;

public class CreateConference {

    private final ConferencePort conferencePort;

    public CreateConference(ConferencePort conferencePort) {
        this.conferencePort = conferencePort;
    }

    public Integer execute(ConferenceParams conferenceParams) throws InvalidIntervalException, InvalidPricesException, InvalidThresholdException, InvalidAttendingDaysException {
        final PriceRanges priceRanges = buildPriceRanges(conferenceParams);
        final PriceGroup priceGroup = buildPriceGroup(conferenceParams.priceGroupParams());
        final List<PriceAttendingDay> priceAttendingDays = buildPriceAttendingDays(conferenceParams);

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

    private static boolean hasPriceGroups(PriceRanges priceRanges, List<PriceAttendingDay> priceAttendingDays) {
        return priceRanges.isEmpty() && priceAttendingDays.isEmpty();
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

    private static PriceRanges buildPriceRanges(ConferenceParams conferenceParams) throws InvalidPricesException {
        return PriceRanges.create(conferenceParams.priceRanges().stream()
                .map(priceRange -> new PriceRange(priceRange.price(), new DateInterval(priceRange.startDate(), priceRange.endDate())))
                .toList());
    }

}
