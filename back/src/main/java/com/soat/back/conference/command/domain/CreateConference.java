package com.soat.back.conference.command.domain;

import java.util.List;

public class CreateConference {

    private final ConferencePort conferencePort;

    public CreateConference(ConferencePort conferencePort) {
        this.conferencePort = conferencePort;
    }

    public Integer execute(ConferenceParams conferenceParams) throws InvalidIntervalException, InvalidPricesException, InvalidThresholdException {
        final List<PriceRange> priceRanges = buildPriceRanges(conferenceParams);
        final PriceGroup priceGroup = buildPriceGroup(conferenceParams.priceGroupParams());
        final List<PriceAttendingDay> priceAttendingDays = buildPriceAttendingDays(conferenceParams);

        Conference conference;
        if (priceRanges.isEmpty() && priceAttendingDays.isEmpty()) {
            conference = Conference.createPriceGroup(
                  conferenceParams.name(),
                  conferenceParams.link(),
                  conferenceParams.price(),
                  conferenceParams.startDate(),
                  conferenceParams.endDate(),
                  priceGroup);
        } else if (priceAttendingDays.isEmpty()) {
            conference = Conference.createWithPriceRanges(
                  conferenceParams.name(),
                  conferenceParams.link(),
                  conferenceParams.price(),
                  conferenceParams.startDate(),
                  conferenceParams.endDate(),
                  priceRanges);
        } else {
            conference = Conference.createWithPriceAttendingDays(
                  conferenceParams.name(),
                  conferenceParams.link(),
                  conferenceParams.price(),
                  conferenceParams.startDate(),
                  conferenceParams.endDate(),
                  priceAttendingDays
            );
        }
        return conferencePort.save(conference);
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
