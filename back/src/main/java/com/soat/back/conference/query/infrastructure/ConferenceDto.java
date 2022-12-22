package com.soat.back.conference.query.infrastructure;

import com.soat.back.conference.query.domain.Conference;

import java.time.LocalDate;

public record ConferenceDto(
        Integer id,
        String name,
        LocalDate startDate,
        LocalDate endDate,
        float fullPrice,
        Boolean isOnline,
        String city,
        String country) implements Conference {
}
