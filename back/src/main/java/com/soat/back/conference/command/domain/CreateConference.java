package com.soat.back.conference.command.domain;

import java.util.List;
import java.util.stream.Collectors;

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

        if (hasPriceGroups(priceRanges, priceAttendingDays)) {
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
                    priceAttendingDays
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
