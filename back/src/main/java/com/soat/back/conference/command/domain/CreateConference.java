package com.soat.back.conference.command.domain;

import org.springframework.stereotype.Service;

@Service
public class CreateConference {

    private final ConferencePort conferencePort;

    public CreateConference(ConferencePort conferencePort) {
        this.conferencePort = conferencePort;
    }

    public Integer execute(Conference conference) {
        conference.priceRanges().stream().map(PriceRange::dateInterval).reduce((dateInterval, dateInterval2) -> {
            if (!dateInterval2.startDate().minusDays(1).equals(dateInterval.endDate())){
                throw new IllegalArgumentException();
            }
            return dateInterval2;
        });
        return conferencePort.save(conference);
    }

}
