package com.soat.back.conference.command.domain;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PriceAttendingDays {
    private final List<PriceAttendingDay> values;

    private PriceAttendingDays(List<PriceAttendingDay> values) {
        this.values = values;
    }

    public static PriceAttendingDays createEmpty() {
        return new PriceAttendingDays(List.of());
    }

    public static PriceAttendingDays create(List<PriceAttendingDay> values) throws InvalidAttendingDaysException {
        if (hasDuplicatedAttendingDays(values)) {
            throw new InvalidAttendingDaysException("Attending days must be unique for one conference");
        }

        if (hasDuplicatedPrice(values)) {
            throw new InvalidAttendingDaysException("Price must be unique for one conference");
        }
        return new PriceAttendingDays(values);
    }

    private static boolean hasDuplicatedAttendingDays(List<PriceAttendingDay> priceAttendingDays) {
        return hasDuplicatedProperty(priceAttendingDays, PriceAttendingDay::attendingDay);
    }

    private static boolean hasDuplicatedPrice(List<PriceAttendingDay> priceAttendingDays) {
        return hasDuplicatedProperty(priceAttendingDays, PriceAttendingDay::price);
    }

    private static <R> boolean hasDuplicatedProperty(List<PriceAttendingDay> list, Function<PriceAttendingDay, ? extends R> mapper) {
        return list
                .stream()
                .map(mapper)
                .collect(Collectors.toSet()).size() < list.size();
    }

    public boolean isEmpty() {
        return this.values.isEmpty();
    }

    public List<PriceAttendingDay> getValues() {
        return values;
    }
}
