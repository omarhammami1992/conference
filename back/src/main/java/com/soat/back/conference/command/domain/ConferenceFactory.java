package com.soat.back.conference.command.domain;

import java.time.LocalDate;

public class ConferenceFactory {

    public static Conference create(String name, String link, Float price, LocalDate startDate, java.time.LocalDate endDate,
                                    String city, String country, PriceRanges priceRanges, PriceGroup priceGroup, PriceAttendingDays priceAttendingDays)
            throws InvalidPricesException, InvalidIntervalException, InvalidAttendingDaysException {

        Conference.ConferenceBuilder conferenceBuilder = Conference.builder()
                .name(name)
                .link(link)
                .price(price)
                .startDate(startDate)
                .endDate(endDate)
                .city(city)
                .country(country);

        if (hasPriceGroups(priceRanges, priceAttendingDays)) {
            return conferenceBuilder
                    .priceGroup(priceGroup, price)
                    .priceRanges(PriceRanges.createEmpty())
                    .priceAttendingDays(PriceAttendingDays.createEmpty())
                    .build();

        }

        if (priceAttendingDays.isEmpty()) {
            return conferenceBuilder
                    .priceRanges(priceRanges)
                    .priceAttendingDays(PriceAttendingDays.createEmpty())
                    .build();
        }

        return conferenceBuilder
                .priceAttendingDays(priceAttendingDays)
                .priceRanges(PriceRanges.createEmpty())
                .build();

    }

    private static boolean hasPriceGroups(PriceRanges priceRanges, PriceAttendingDays priceAttendingDays) {
        return priceRanges.isEmpty() && priceAttendingDays.isEmpty();
    }


}
