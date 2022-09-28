package com.soat.back.conference.command.domain;

import com.soat.back.conference.command.application.ConferenceParams;
import org.springframework.stereotype.Service;

@Service
public class CreateConference {

    private final ConferencePort conferencePort;

    public CreateConference(ConferencePort conferencePort) {
        this.conferencePort = conferencePort;
    }

    public Integer execute(ConferenceParams conferenceParams) {
        var priceRanges = conferenceParams.priceRanges().stream()
                .map(priceRange -> new PriceRange(priceRange.price(), new DateInterval(priceRange.startDate(), priceRange.endDate())))
                .toList();
        var conference = new Conference(conferenceParams.name(), conferenceParams.link(), conferenceParams.startDate(), conferenceParams.endDate(), priceRanges);
        return conferencePort.save(conference);
    }

}
