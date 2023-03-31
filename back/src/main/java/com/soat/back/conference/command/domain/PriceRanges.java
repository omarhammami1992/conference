package com.soat.back.conference.command.domain;

import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public final class PriceRanges {
    private final List<PriceRange> values;

    private PriceRanges(List<PriceRange> values) {
        this.values = values;
    }

    public static PriceRanges createEmpty() {
        return new PriceRanges(List.of());
    }

    public static PriceRanges create(List<PriceRange> values) throws InvalidPricesException {
        if (checkMinimumPriceGreaterThanZero(values)) {
            throw new InvalidPricesException("Price range greater than zero");
        }

        if (checkUniquenessPriceInPriceRange(values)) {
            throw new InvalidPricesException("Non unique price range");
        }
        return new PriceRanges(values);
    }

    void checkIntervals(Float price) throws InvalidIntervalException, InvalidPricesException {
        for (int i = 0; i < this.values.size() - 1; i++) {
            checkIntervalDates(i);
            checkIntervalsPrices(i);
        }
        checkPrices(price);
    }

    private void checkIntervalDates(int i) throws InvalidIntervalException {
        if (!values.get(i + 1).dateInterval().startDate().minusDays(1).equals(values.get(i).dateInterval().endDate())) {
            throw new InvalidIntervalException();
        }
    }

    private void checkIntervalsPrices(int i) throws InvalidPricesException {
        if (values.get(i).price() > values.get(i + 1).price()) {
            throw new InvalidPricesException("Price range should be strictly ascending");
        }
    }

    private void checkPrices(Float price) throws InvalidPricesException {
        if (!CollectionUtils.isEmpty(values) && price < values.get(values.size() - 1).price()) {
            throw new InvalidPricesException("Price range must be lower than default price");
        }
    }

    private static boolean checkMinimumPriceGreaterThanZero(List<PriceRange> priceRanges) {
        return priceRanges.stream()
                .mapToDouble(PriceRange::price)
                .anyMatch(price -> price < 0);
    }

    private static boolean checkUniquenessPriceInPriceRange(List<PriceRange> priceRanges) {
        return priceRanges
                .stream()
                .map(PriceRange::price)
                .collect(Collectors.toSet())
                .size() != (long) priceRanges.size();
    }

    public List<PriceRange> getValues() {
        return values;
    }

    @Override
    public String toString() {
        return "PriceRanges[" +
                "priceRanges=" + values + ']';
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }
}

