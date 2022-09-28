package com.soat.back.conference.command.domain;

import java.time.LocalDate;
import java.util.List;

public record Conference(String name, String link, LocalDate startDate, LocalDate endDate,
                         List<PriceRange> priceRanges) {
    public Conference {
        priceRanges.stream().map(PriceRange::dateInterval).reduce((current, next) -> {
            if (!next.startDate().minusDays(1).equals(current.endDate())){
                throw new IllegalArgumentException();
            }
            return next;
        });

    }
}
