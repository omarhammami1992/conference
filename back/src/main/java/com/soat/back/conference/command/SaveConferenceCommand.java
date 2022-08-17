package com.soat.back.conference.command;

import com.soat.back.common.domain.cqrs.Command;

import java.time.LocalDate;

public record SaveConferenceCommand(String name, String link, LocalDate startDate, LocalDate endDate) implements Command {
}
