package com.soat.back.conference.command.domain;

import java.time.LocalDate;
import java.util.List;

public record Conference(String name, String link, LocalDate startDate, LocalDate endDate, List<PricingRange> pricingRanges) {
}
