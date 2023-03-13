package com.soat.back.conference.command.domain;

import static java.util.Collections.emptyList;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class Conference {
    private final String name;
    private final String link;

    private final Float price;
    private final LocalDate startDate;
    private final LocalDate endDate;

    private final List<PriceRange> priceRanges;
    private String city;
    private String country;
    private final PriceGroup priceGroup;
    private final List<PriceAttendingDay> priceAttendingDays;

    private Conference(String name, String link, Float price, List<PriceRange> priceRanges, LocalDate startDate, LocalDate endDate, String city, String country) {
        this.name = name;
        this.link = link;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priceRanges = priceRanges;
        this.city = city;
        this.country = country;
        this.priceGroup = null;
        this.priceAttendingDays = emptyList();
    }

    private Conference(String name, String link, Float price, LocalDate startDate, LocalDate endDate, String city, String country, PriceGroup priceGroup) {
        this.name = name;
        this.link = link;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.city = city;
        this.country = country;
        this.priceRanges = emptyList();
        this.priceGroup = priceGroup;
        this.priceAttendingDays = emptyList();
    }

    private Conference(String name, String link, Float price, LocalDate startDate, LocalDate endDate, List<PriceAttendingDay> priceAttendingDays, String city, String country) {
        this.name = name;
        this.link = link;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.city = city;
        this.country = country;
        this.priceGroup = null;
        this.priceRanges = emptyList();
        this.priceAttendingDays = priceAttendingDays;
    }

    private Conference(String name, String link, Float price, LocalDate startDate, LocalDate endDate, List<PriceAttendingDay> priceAttendingDays) {
        this.name = name;
        this.link = link;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priceRanges = emptyList();
        this.priceGroup = null;
        this.priceAttendingDays = priceAttendingDays;
    }

    public static Conference createWithPriceRanges(String name, String link, Float price, LocalDate startDate, LocalDate endDate, List<PriceRange> priceRanges, String city, String country) throws InvalidIntervalException, InvalidPricesException {
        checkIntervals(priceRanges, price);
        return new Conference(name, link, price, priceRanges, startDate, endDate, city, country);
    }

    public static Conference createPriceGroup(String name, String link, Float price, LocalDate startDate, LocalDate endDate, PriceGroup priceGroup, String city, String country) throws InvalidPricesException, InvalidThresholdException {
        checkPriceGroupAmount(price, priceGroup);
        checkPriceGroupThreshold(priceGroup);
        return new Conference(name, link, price, startDate, endDate,city, country , priceGroup);
    }

    public static Conference createWithPriceAttendingDays(String name, String link, Float price, LocalDate startDate, LocalDate endDate, List<PriceAttendingDay> priceAttendingDays, String city, String country) throws InvalidAttendingDaysException {
        if (ifIsAttendingDays(startDate, endDate, priceAttendingDays))
            throw new InvalidAttendingDaysException("Attending days must be equal or less than conference duration");

        if (hasDuplicatedAttendingDays(priceAttendingDays)) {
            throw new InvalidAttendingDaysException("Attending days must be unique for one conference");
        }

        if (hasDuplicatedPrice(priceAttendingDays)) {
            throw new InvalidAttendingDaysException("Price must be unique for one conference");
        }
        return new Conference(name, link, price, startDate, endDate, priceAttendingDays, city, country);
    }

    private static boolean hasDuplicatedAttendingDays(List<PriceAttendingDay> priceAttendingDays) {
        return hasDuplicatedProperty(priceAttendingDays,PriceAttendingDay::attendingDay);
    }

    private static boolean hasDuplicatedPrice(List<PriceAttendingDay> priceAttendingDays) {
        return hasDuplicatedProperty(priceAttendingDays,PriceAttendingDay::price);
    }

    private static <R> boolean hasDuplicatedProperty(List<PriceAttendingDay> list, Function<PriceAttendingDay, ? extends R> mapper) {
        return list
                .stream()
                .map(mapper)
                .collect(Collectors.toSet()).size() < list.size();
    }

        private static boolean ifIsAttendingDays(LocalDate startDate, LocalDate endDate, List<PriceAttendingDay> priceAttendingDays) {
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        var maxAttending = priceAttendingDays
                .stream()
                .max(Comparator.comparing(PriceAttendingDay::attendingDay))
                .map(PriceAttendingDay::attendingDay)
                .orElse(0f);
        return maxAttending > daysBetween;
    }


    private static void checkPriceGroupThreshold(PriceGroup priceGroup) throws InvalidThresholdException {
        if (priceGroup.threshold() < 2) {
            throw new InvalidThresholdException("Price group threshold must be greater than 1");
        }
    }

    private static void checkPriceGroupAmount(Float price, PriceGroup priceGroup) throws InvalidPricesException {
        if (priceGroup.price() > price) {
            throw new InvalidPricesException("Price group is greater than default price");
        }
    }

    private static void checkIntervals(List<PriceRange> priceRanges, Float price) throws InvalidIntervalException, InvalidPricesException {
        for (int i = 0; i < priceRanges.size() - 1; i++) {
            checkIntervalDates(priceRanges, i);
            checkIntervalsPrices(priceRanges, i);
        }
        checkPrices(priceRanges, price);
    }

    private static void checkIntervalDates(List<PriceRange> priceRanges, int i) throws InvalidIntervalException {
        if (!priceRanges.get(i + 1).dateInterval().startDate().minusDays(1).equals(priceRanges.get(i).dateInterval().endDate())) {
            throw new InvalidIntervalException();
        }
    }

    private static void checkIntervalsPrices(List<PriceRange> priceRanges, int i) throws InvalidPricesException {
        if (priceRanges.get(i).price() > priceRanges.get(i + 1).price()) {
            throw new InvalidPricesException("Price range should be strictly ascending");
        }
    }

    private static void checkPrices(List<PriceRange> priceRanges, Float price) throws InvalidPricesException {
        if (priceRanges != null && price < priceRanges.get(priceRanges.size() - 1).price()) {
            throw new InvalidPricesException("Price range must be lower than default price");
        }
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

    public List<PriceRange> getPriceRanges() {
        return priceRanges;
    }

    public PriceGroup getPriceGroup() {
        return priceGroup;
    }

    public List<PriceAttendingDay> getPriceAttendingDays() {
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
