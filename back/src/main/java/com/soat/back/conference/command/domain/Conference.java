package com.soat.back.conference.command.domain;

import java.time.LocalDate;

public record Conference(String name, String link, LocalDate startDate, LocalDate endDate,
                         java.util.List<PriceRange> priceRanges) {
}
