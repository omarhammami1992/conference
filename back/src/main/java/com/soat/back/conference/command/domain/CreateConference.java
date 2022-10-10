package com.soat.back.conference.command.domain;

import java.util.List;

public class CreateConference {

    private final ConferencePort conferencePort;

    public CreateConference(ConferencePort conferencePort) {
        this.conferencePort = conferencePort;
    }

    public Integer execute(ConferenceParams conferenceParams) throws InvalidIntervalException {
        final List<PriceRange> priceRanges = buildPriceRanges(conferenceParams);
        PriceGroup priceGroup = getPriceGroup(conferenceParams);

        var conference = new Conference(conferenceParams.name(),
              conferenceParams.link(),
              conferenceParams.price(),
              conferenceParams.startDate(),
              conferenceParams.endDate(),
              priceRanges,
              priceGroup
        );
        return conferencePort.save(conference);
    }

    private static PriceGroup getPriceGroup(ConferenceParams conferenceParams) {
        PriceGroup priceGroup = null;
        if (conferenceParams.priceGroup() != null && conferenceParams.participantsThreshold() != null) {
            priceGroup = new PriceGroup(conferenceParams.priceGroup(), conferenceParams.participantsThreshold());
        }
        return priceGroup;
    }

    private static List<PriceRange> buildPriceRanges(ConferenceParams conferenceParams) {
        return conferenceParams.priceRanges().stream()
              .map(priceRange -> new PriceRange(priceRange.price(), new DateInterval(priceRange.startDate(), priceRange.endDate())))
                .toList();
    }

}
