package com.soat.back.conference.command.domain;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreateConference {

    private final ConferencePort conferencePort;

    public CreateConference(ConferencePort conferencePort) {
        this.conferencePort = conferencePort;
    }

    public Integer execute(ConferenceParams conferenceParams) throws InvalidIntervalException {
        final List<PriceRange> priceRanges = buildPriceRanges(conferenceParams);
        final PriceGroup priceGroup = new PriceGroup(conferenceParams.priceGroup(), conferenceParams.participantsThreshold());

        var conference = new Conference(conferenceParams.name(), conferenceParams.link(), conferenceParams.price(), conferenceParams.startDate(), conferenceParams.endDate(), priceRanges, priceGroup);
        return conferencePort.save(conference);
    }

    private static List<PriceRange> buildPriceRanges(ConferenceParams conferenceParams) {
        return conferenceParams.priceRanges().stream()
                .map(priceRange -> new PriceRange(priceRange.price(), new DateInterval(priceRange.startDate(), priceRange.endDate())))
                .toList();
    }

}
