package com.soat.back.conference.query.application;

import java.time.LocalDate;

public record ConferenceJson(
        Integer id,
        String name,
        String link,
        LocalDate startDate,
        LocalDate endDate,
        float fullPrice,
        Boolean isOnline,
        AddressJson address) {
}
