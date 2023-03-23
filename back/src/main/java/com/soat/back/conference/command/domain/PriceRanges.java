package com.soat.back.conference.command.domain;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class PriceRanges {
    private final List<PriceRange> priceRanges;

    public PriceRanges(List<PriceRange> priceRanges) throws InvalidPricesException {
        if (checkMinimumPriceGreaterThanZero(priceRanges)) {
            throw new InvalidPricesException("Price range greater than zero");
        }

        if (checkUniquenessPriceInPriceRange(priceRanges)) {
            throw new InvalidPricesException("Non unique price range");
        }
        this.priceRanges = priceRanges;
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
                .size() != priceRanges.stream().count();
    }

    public List<PriceRange> getPriceRanges() {
        return priceRanges;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (PriceRanges) obj;
        return Objects.equals(this.priceRanges, that.priceRanges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(priceRanges);
    }

    @Override
    public String toString() {
        return "PriceRanges[" +
                "priceRanges=" + priceRanges + ']';
    }

}

