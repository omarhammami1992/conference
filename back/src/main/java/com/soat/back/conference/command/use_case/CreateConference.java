package com.soat.back.conference.command.use_case;

import com.soat.back.conference.command.domain.*;

import java.util.Objects;

public class CreateConference {

    private final ConferencePort conferencePort;

    public CreateConference(ConferencePort conferencePort) {
        this.conferencePort = conferencePort;
    }

    public Integer execute(ConferenceParams conferenceParams) throws InvalidIntervalException, InvalidPricesException, InvalidThresholdException, InvalidAttendingDaysException {
        final PriceRanges priceRanges = conferenceParams.priceRanges();
        final PriceGroup priceGroup = conferenceParams.priceGroup();
        final PriceAttendingDays priceAttendingDays = conferenceParams.priceAttendingDays();

        Conference.ConferenceBuilder conferenceBuilder = Conference.builder()
                .name(conferenceParams.name())
                .link(conferenceParams.link())
                .price(conferenceParams.price())
                .startDate(conferenceParams.startDate())
                .endDate(conferenceParams.endDate())
                .city(conferenceParams.city())
                .country(conferenceParams.country());

        if (Objects.nonNull(priceGroup)) {
            conferenceBuilder.priceGroup(priceGroup, conferenceParams.price());
        } else if (!priceRanges.isEmpty()) {
            conferenceBuilder.priceRanges(priceRanges);
        } else if(!priceAttendingDays.isEmpty()){
            conferenceBuilder.priceAttendingDays(priceAttendingDays);
        }

        return conferencePort.save(conferenceBuilder.build());
    }

}
