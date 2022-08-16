package com.soat.back.domain;

import com.soat.back.cqrs.Command;

public record SaveConferenceCommand(String name, String link) implements Command {
}
