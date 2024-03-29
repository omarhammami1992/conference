package com.soat.back.conference.command.domain;

public final class PriceGroup {
    private final Float price;
    private final Integer threshold;

    private PriceGroup(Float price, Integer threshold) {
        this.price = price;
        this.threshold = threshold;
    }

    public static PriceGroup create(Float price, Integer threshold) throws InvalidThresholdException {
        if (threshold < 2) {
            throw new InvalidThresholdException("Price group threshold must be greater than 1");
        }
        return new PriceGroup(price, threshold);
    }

    public static PriceGroup createEmpty() {
        return new PriceGroup(0F, 0);
    }

    public void checkPriceGroupAmount(Float defaultPrice) throws InvalidPricesException {
        if (this.price > defaultPrice) {
            throw new InvalidPricesException("Price group is greater than default price");
        }
    }

    public Float price() {
        return price;
    }

    public Integer threshold() {
        return threshold;
    }
}
