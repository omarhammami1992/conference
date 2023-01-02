package com.soat.back.conference.command.domain;

import java.time.LocalDate;

public record PriceRangeParams(Float price, LocalDate startDate, LocalDate endDate) {
}
