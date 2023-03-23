package com.soat.back.conference.command.domain;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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

    private Conference(String name, String link, Float price, PriceRanges priceRanges, LocalDate startDate, LocalDate endDate, String city, String country) {
        this.name = name;
        this.link = link;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priceRanges = priceRanges;
        this.city = city;
        this.country = country;
        this.priceGroup = null;
        this.priceAttendingDays = PriceAttendingDays.createEmpty();
    }

    private Conference(String name, String link, Float price, LocalDate startDate, LocalDate endDate, String city, String country, PriceGroup priceGroup) {
        this.name = name;
        this.link = link;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.city = city;
        this.country = country;
        this.priceRanges = PriceRanges.createEmpty();
        this.priceGroup = priceGroup;
        this.priceAttendingDays = PriceAttendingDays.createEmpty();
    }

    private Conference(String name, String link, Float price, LocalDate startDate, LocalDate endDate, PriceAttendingDays priceAttendingDays, String city, String country) {
        this.name = name;
        this.link = link;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.city = city;
        this.country = country;
        this.priceGroup = null;
        this.priceRanges = PriceRanges.createEmpty();
        this.priceAttendingDays = priceAttendingDays;
    }

    public static Conference createWithPriceRanges(String name, String link, Float price, LocalDate startDate, LocalDate endDate, PriceRanges priceRanges, String city, String country) throws InvalidIntervalException, InvalidPricesException {
        priceRanges.checkIntervals(price);
        return new Conference(name, link, price, priceRanges, startDate, endDate, city, country);
    }

    public static Conference createPriceGroup(String name, String link, Float price, LocalDate startDate, LocalDate endDate, PriceGroup priceGroup, String city, String country) throws InvalidPricesException {
        priceGroup.checkPriceGroupAmount(price);
        return new Conference(name, link, price, startDate, endDate, city, country, priceGroup);
    }

    public static Conference createWithPriceAttendingDays(String name, String link, Float price, LocalDate startDate, LocalDate endDate, PriceAttendingDays priceAttendingDays, String city, String country) throws InvalidAttendingDaysException {
        float period = ChronoUnit.DAYS.between(startDate, endDate) + 1f;
        priceAttendingDays.checkAreAllBelow(period);
        return new Conference(name, link, price, startDate, endDate, priceAttendingDays, city, country);
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
