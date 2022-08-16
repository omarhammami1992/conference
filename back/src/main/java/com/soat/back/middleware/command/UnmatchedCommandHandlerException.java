package com.soat.back.middleware.command;


import com.soat.back.cqrs.Command;

public class UnmatchedCommandHandlerException extends RuntimeException {
    public <C extends Command> UnmatchedCommandHandlerException(C command) {

    }
}
