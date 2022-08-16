package com.soat.back.common.infrastructure.middleware.command;


import com.soat.back.common.domain.cqrs.Command;

public class UnmatchedCommandHandlerException extends RuntimeException {
    public <C extends Command> UnmatchedCommandHandlerException(C command) {

    }
}
