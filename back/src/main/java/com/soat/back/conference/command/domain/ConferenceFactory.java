package com.soat.back.conference.command.domain;

import java.time.LocalDate;

public class ConferenceFactory {

    public Conference create(String name, String link, Float price, LocalDate startDate, java.time.LocalDate endDate,
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
            return conferenceBuilder.priceRanges(priceRanges).build();

        }
        if (priceAttendingDays.isEmpty()) {
            return Conference.createWithPriceRanges(
                    name,
                    link,
                    price,
                    startDate,
                    endDate,
                    priceRanges,
                    city,
                    country);
        }
        return Conference.createWithPriceAttendingDays(
                name,
                link,
                price,
                startDate,
                endDate,
                priceAttendingDays,
                city,
                country
        );
    }

    private static boolean hasPriceGroups(PriceRanges priceRanges, PriceAttendingDays priceAttendingDays) {
        return priceRanges.isEmpty() && priceAttendingDays.isEmpty();
    }


}
