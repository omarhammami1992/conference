package com.soat.back.conference.command.domain;

import java.time.LocalDate;

public final class Conference {
    private final String name;
    private final String link;
    private final Float price;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final PriceRanges priceRanges;
    private final String city;
    private final String country;
    private final PriceGroup priceGroup;
    private final PriceAttendingDays priceAttendingDays;

    private Conference(String name,
                      String link,
                      Float price,
                      LocalDate startDate,
                      LocalDate endDate,
                      String city,
                      String country,
                      PriceRanges priceRanges,
                      PriceGroup priceGroup,
                      PriceAttendingDays priceAttendingDays) {
        this.name = name;
        this.link = link;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priceRanges = priceRanges;
        this.city = city;
        this.country = country;
        this.priceGroup = priceGroup;
        this.priceAttendingDays = priceAttendingDays;
    }

    private static Conference createWithPriceRanges(String name, String link, Float price, LocalDate startDate, LocalDate endDate, PriceRanges priceRanges, String city, String country) throws InvalidIntervalException, InvalidPricesException {
        priceRanges.checkIntervals(price);
        return new Conference(name, link, price, startDate, endDate, city, country, priceRanges, null, PriceAttendingDays.createEmpty());
    }

    private static Conference createPriceGroup(String name, String link, Float price, LocalDate startDate, LocalDate endDate, PriceGroup priceGroup, String city, String country) throws InvalidPricesException {
        priceGroup.checkPriceGroupAmount(price);
        return new Conference(name, link, price, startDate, endDate, city, country, PriceRanges.createEmpty(), priceGroup, PriceAttendingDays.createEmpty());
    }

    private static Conference createWithPriceAttendingDays(String name, String link, Float price, LocalDate startDate, LocalDate endDate, PriceAttendingDays priceAttendingDays, String city, String country) throws InvalidAttendingDaysException {
        var interval = new DateInterval(startDate, endDate);
        priceAttendingDays.checkAreAllBelow(interval.period());
        return new Conference(name, link, price, startDate, endDate, city, country, PriceRanges.createEmpty(), null, priceAttendingDays);
    }

    public static Conference create(String name, String link, Float price, LocalDate startDate, LocalDate endDate, String city, String country, PriceRanges priceRanges, PriceGroup priceGroup, PriceAttendingDays priceAttendingDays) throws InvalidPricesException, InvalidIntervalException, InvalidAttendingDaysException {
        if (hasPriceGroups(priceRanges, priceAttendingDays)) {
            return Conference.createPriceGroup(
                    name,
                    link,
                    price,
                    startDate,
                    endDate,
                    priceGroup,
                    city,
                    country);
        } else if (priceAttendingDays.isEmpty()) {
            return Conference.createWithPriceRanges(
                    name,
                    link,
                    price,
                    startDate,
                    endDate,
                    priceRanges,
                    city,
                    country);
        } else {
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

    }

    private static boolean hasPriceGroups(PriceRanges priceRanges, PriceAttendingDays priceAttendingDays) {
        return priceRanges.isEmpty() && priceAttendingDays.isEmpty();
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public Float getPrice() {
        return price;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public PriceRanges getPriceRanges() {
        return priceRanges;
    }

    public PriceGroup getPriceGroup() {
        return priceGroup;
    }

    public PriceAttendingDays getPriceAttendingDays() {
        return priceAttendingDays;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return "Conference{" +
                "name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", price=" + price +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", priceRanges=" + priceRanges +
                ", priceGroup=" + priceGroup +
                '}';
    }
}
