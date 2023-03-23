package com.soat.back.conference.command.domain;

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

