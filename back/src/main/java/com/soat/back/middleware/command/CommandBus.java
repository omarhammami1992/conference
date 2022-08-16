package com.soat.back.middleware.command;

import com.soat.back.cqrs.Command;
import com.soat.back.cqrs.CommandResponse;

public interface CommandBus {
    <R extends CommandResponse, C extends Command> R dispatch(C command);
}
