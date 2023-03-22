package com.soat.back.conference.command.domain;

import java.util.List;
import java.util.stream.Collectors;

public final class PriceRanges {
    private final List<PriceRange> values;

    public PriceRanges() {
        values = List.of();
    }

    public PriceRanges(List<PriceRange> values) throws InvalidPricesException {
        if (checkMinimumPriceGreaterThanZero(values)) {
            throw new InvalidPricesException("Price range greater than zero");
        }

        if (checkUniquenessPriceInPriceRange(values)) {
            throw new InvalidPricesException("Non unique price range");
        }
        this.values = values;
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

