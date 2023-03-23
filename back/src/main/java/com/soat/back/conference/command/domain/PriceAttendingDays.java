package com.soat.back.conference.command.domain;

import java.util.List;

public class PriceAttendingDays {
    private final List<PriceAttendingDay> values;

    private PriceAttendingDays(List<PriceAttendingDay> values) {
        this.values = values;
    }

    public static PriceAttendingDays createEmpty() {
        return new PriceAttendingDays(List.of());
    }

    public static PriceAttendingDays create(List<PriceAttendingDay> values) {
        return new PriceAttendingDays(values);
    }

}
