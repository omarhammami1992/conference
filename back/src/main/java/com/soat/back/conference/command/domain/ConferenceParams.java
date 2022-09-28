package com.soat.back.conference.command.domain;

import java.time.LocalDate;
import java.util.List;

public record ConferenceParams(String name,
                               String link,
                               LocalDate startDate,
                               LocalDate endDate,
                               Float price,
                               List<PriceRangeParams> priceRanges,
                               Float priceGroup, Integer participantsThreshold) {
}
