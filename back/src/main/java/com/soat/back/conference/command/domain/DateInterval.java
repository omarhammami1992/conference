package com.soat.back.conference.command.domain;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public record DateInterval(LocalDate startDate, LocalDate endDate) {
    public float period() {
        return  ChronoUnit.DAYS.between(startDate, endDate) + 1f;
    }
}
