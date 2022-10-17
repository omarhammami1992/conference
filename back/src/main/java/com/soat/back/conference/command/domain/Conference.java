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

    private Conference(String name, String link, Float price, LocalDate startDate, LocalDate endDate, List<PriceRange> priceRanges) {
        this.name = name;
        this.link = link;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priceRanges = priceRanges;
        this.priceGroup = null;
    }

    private Conference(String name, String link, Float price, LocalDate startDate, LocalDate endDate, PriceGroup priceGroup) {
        this.name = name;
        this.link = link;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priceRanges = emptyList();
        this.priceGroup = priceGroup;
    }

    public static Conference createWithPriceRanges(String name, String link, Float price, LocalDate startDate, LocalDate endDate, List<PriceRange> priceRanges) throws InvalidIntervalException {
        checkIntervals(priceRanges, price);
        return new Conference(name, link, price, startDate, endDate, priceRanges);
    }

    public static Conference createPriceGroup(String name, String link, Float price, LocalDate startDate, LocalDate endDate, PriceGroup priceGroup) {
        return new Conference(name, link, price, startDate, endDate, priceGroup);
    }

    private static void checkIntervals(List<PriceRange> priceRanges, Float price) throws InvalidIntervalException {
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

    private static void checkIntervalsPrices(List<PriceRange> priceRanges, int i) throws InvalidIntervalException {
        if (priceRanges.get(i).price() > priceRanges.get(i + 1).price()) {
            throw new InvalidIntervalException();
        }
    }

    private static void checkPrices(List<PriceRange> priceRanges, Float price) throws InvalidIntervalException  {
        if (priceRanges != null && price < priceRanges.get(priceRanges.size() - 1).price()) {
            throw new InvalidIntervalException();
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
