package com.soat.back.conference.command;

import java.time.LocalDate;
import com.soat.back.conference.command.domain.Conference;

public record ConferenceFake(String name, String link, LocalDate startDate, LocalDate endDate) implements Conference {
}
