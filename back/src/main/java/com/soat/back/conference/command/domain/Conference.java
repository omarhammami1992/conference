package com.soat.back.conference.command.domain;

import static java.util.Collections.emptyList;

import java.time.LocalDate;
import java.util.List;

public final class Conference {
    private final String name;
    private final String link;

    private final Float price;
    private final LocalDate startDate;
    private final LocalDate endDate;

    private final List<PriceRange> priceRanges;
    private final PriceGroup priceGroup;
    private final List<PriceAttendingDay> priceAttendingDays;

    private Conference(String name, String link, Float price, List<PriceRange> priceRanges, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.link = link;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priceRanges = priceRanges;
        this.priceGroup = null;
        this.priceAttendingDays = emptyList();
    }

    private Conference(String name, String link, Float price, LocalDate startDate, LocalDate endDate, PriceGroup priceGroup) {
        this.name = name;
        this.link = link;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priceRanges = emptyList();
        this.priceGroup = priceGroup;
        this.priceAttendingDays = emptyList();
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

    public static Conference createWithPriceRanges(String name, String link, Float price, LocalDate startDate, LocalDate endDate, List<PriceRange> priceRanges) throws InvalidIntervalException, InvalidPricesException {
        checkIntervals(priceRanges, price);
        return new Conference(name, link, price, priceRanges, startDate, endDate);
    }

    public static Conference createPriceGroup(String name, String link, Float price, LocalDate startDate, LocalDate endDate, PriceGroup priceGroup) throws InvalidPricesException, InvalidThresholdException {
        checkPriceGroupAmount(price, priceGroup);
        checkPriceGroupThreshold(priceGroup);
        return new Conference(name, link, price, startDate, endDate, priceGroup);
    }

    public static Conference createWithPriceAttendingDays(String name, String link, Float price, LocalDate startDate, LocalDate endDate, List<PriceAttendingDay> priceAttendingDays) {
        return new Conference(name, link, price, startDate, endDate, priceAttendingDays);
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

    private static void checkPrices(List<PriceRange> priceRanges, Float price) throws InvalidPricesException  {
        if (priceRanges != null && price < priceRanges.get(priceRanges.size() - 1).price()) {
            throw new InvalidPricesException("Price range must be lower than default price");
        }
    }

    public String name() {
        return name;
    }

    public String link() {
        return link;
    }

    public Float getPrice() {
        return price;
    }

    public LocalDate startDate() {
        return startDate;
    }

    public LocalDate endDate() {
        return endDate;
    }

    public List<PriceRange> priceRanges() {
        return priceRanges;
    }

    public PriceGroup getPriceGroup() {
        return priceGroup;
    }

    public List<PriceAttendingDay> getPriceAttendingDays() {
        return priceAttendingDays;
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
