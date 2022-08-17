package com.soat.back.common.application;

import com.soat.back.middleware.command.CommandBus;
import com.soat.back.middleware.command.CommandBusFactory;

public abstract class CommandController {
    private CommandBus commandBus;
    private final CommandBusFactory commandBusFactory;

    public CommandController(CommandBusFactory commandBusFactory) {
        this.commandBusFactory = commandBusFactory;
    }

    protected CommandBus getCommandBus() {
        if (commandBus == null) {
            this.commandBus = commandBusFactory.build();
        }
        return commandBus;
    }
}