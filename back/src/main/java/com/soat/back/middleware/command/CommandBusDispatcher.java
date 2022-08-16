package com.soat.back.middleware.command;

import com.soat.back.cqrs.Command;
import com.soat.back.cqrs.CommandHandler;
import com.soat.back.cqrs.CommandResponse;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

public class CommandBusDispatcher implements CommandBus {
    private final Map<Class, CommandHandler> commandHandlers;

    public CommandBusDispatcher(List<? extends CommandHandler> commandHandlers) {
        this.commandHandlers = commandHandlers.stream().collect(Collectors
                .toMap(CommandHandler::listenTo, commandHandler -> commandHandler));
    }

    @Override
    public <R extends CommandResponse, C extends Command> R dispatch(C command) {
        CommandHandler<C, R> commandHandler = this.commandHandlers.get(command.getClass());
        return ofNullable(commandHandler)
                .map(handler -> handler.handle(command))
                .orElseThrow(() -> new UnmatchedCommandHandlerException(command));
    }
}
