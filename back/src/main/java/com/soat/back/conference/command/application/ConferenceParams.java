package com.soat.back.conference.command.application;

import com.soat.back.conference.command.domain.PriceRange;

import java.time.LocalDate;
import java.util.List;

public record ConferenceParams(String name, String link, LocalDate startDate, LocalDate endDate,
                               List<PriceRangeParams> priceRanges) {
}
