package com.soat.back.conference.command.domain;

import java.time.LocalDate;

public record DateInterval(LocalDate startDate, LocalDate endDate) {
}
