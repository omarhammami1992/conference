package com.soat.back.conference.query.domain;

import com.soat.back.common.domain.ConferenceId;

import java.time.LocalDate;

public record Conference(ConferenceId id, String name, String link, LocalDate startDate, LocalDate endDate) {
}
