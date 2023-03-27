package com.soat.back.conference.command.use_case;

import com.soat.back.conference.command.domain.*;

public class CreateConference {

    private final ConferencePort conferencePort;

    public CreateConference(ConferencePort conferencePort) {
        this.conferencePort = conferencePort;
    }

    public Integer execute(ConferenceParams conferenceParams) throws InvalidIntervalException, InvalidPricesException, InvalidThresholdException, InvalidAttendingDaysException {
        final PriceRanges priceRanges = buildPriceRanges(conferenceParams);
        final PriceGroup priceGroup = buildPriceGroup(conferenceParams.priceGroupParams());
        final PriceAttendingDays priceAttendingDays = buildPriceAttendingDays(conferenceParams);

        Conference conference = ConferenceFactory.create(
                conferenceParams.name(),
                conferenceParams.link(),
                conferenceParams.price(),
                conferenceParams.startDate(),
                conferenceParams.endDate(),
                conferenceParams.city(),
                conferenceParams.country(),
                priceRanges,
                priceGroup,
                priceAttendingDays
        );

        return conferencePort.save(conference);
    }

    private PriceAttendingDays buildPriceAttendingDays(ConferenceParams conferenceParams) throws InvalidAttendingDaysException {
        return PriceAttendingDays.create(conferenceParams.priceAttendingDaysParams().stream()
                .map(priceAttendingDay -> new PriceAttendingDay(priceAttendingDay.price(), priceAttendingDay.attendingDays()))
                .toList());
    }

    private static PriceGroup buildPriceGroup(PriceGroupParams priceGroupParams) throws InvalidThresholdException {
        PriceGroup priceGroup = null;
        if (priceGroupParams != null) {
            priceGroup = PriceGroup.create(priceGroupParams.priceGroup(), priceGroupParams.participantsThreshold());
        }
        return priceGroup;
    }

    private static PriceRanges buildPriceRanges(ConferenceParams conferenceParams) throws InvalidPricesException {
        return PriceRanges.create(conferenceParams.priceRanges().stream()
                .map(priceRange -> new PriceRange(priceRange.price(), new DateInterval(priceRange.startDate(), priceRange.endDate())))
                .toList());
    }

}
