package com.soat.back.conference.command.domain;

import java.time.LocalDate;

public record PricingRange(LocalDate startDate, LocalDate endDate, Double price) {
}
