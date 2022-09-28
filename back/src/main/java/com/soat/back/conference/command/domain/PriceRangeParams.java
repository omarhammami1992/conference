package com.soat.back.conference.command.domain;

import com.soat.back.conference.command.domain.DateInterval;

import java.time.LocalDate;

public record PriceRangeParams(Float price, LocalDate startDate, LocalDate endDate) {
}
