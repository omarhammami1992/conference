package com.soat.back.conference.command.domain;

import java.time.LocalDate;

public record ConferenceToSave(String name, String link, LocalDate startDate, LocalDate endDate) {
}
