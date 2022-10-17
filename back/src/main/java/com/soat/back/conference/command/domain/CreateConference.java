package com.soat.back.conference.command.domain;

import java.util.List;

public class CreateConference {

    private final ConferencePort conferencePort;

    public CreateConference(ConferencePort conferencePort) {
        this.conferencePort = conferencePort;
    }

    public Integer execute(ConferenceParams conferenceParams) throws InvalidIntervalException {
        final List<PriceRange> priceRanges = buildPriceRanges(conferenceParams);
        final PriceGroup priceGroup = buildPriceGroup(conferenceParams.priceGroupParams());

        Conference conference;
        if (priceRanges.isEmpty()) {
            conference = Conference.createPriceGroup(
                  conferenceParams.name(),
                  conferenceParams.link(),
                  conferenceParams.price(),
                  conferenceParams.startDate(),
                  conferenceParams.endDate(),
                  priceGroup);
        } else {
            conference = Conference.createWithPriceRanges(
                  conferenceParams.name(),
                  conferenceParams.link(),
                  conferenceParams.price(),
                  conferenceParams.startDate(),
                  conferenceParams.endDate(),
                  priceRanges);
        }
        return conferencePort.save(conference);
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
