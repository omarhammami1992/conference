package com.soat.back.common.infrastructure.middleware.command;

import com.soat.back.common.domain.cqrs.Command;
import com.soat.back.common.domain.cqrs.CommandResponse;

public interface CommandBus {
    <R extends CommandResponse, C extends Command> R dispatch(C command);
}
