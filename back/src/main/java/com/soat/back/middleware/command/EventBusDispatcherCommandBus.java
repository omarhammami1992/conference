package com.soat.back.middleware.command;

import com.soat.back.common.domain.cqrs.Command;
import com.soat.back.common.domain.cqrs.CommandResponse;
import com.soat.back.common.domain.cqrs.Event;
import com.soat.back.middleware.event.EventBus;

import java.util.concurrent.atomic.AtomicReference;

public class EventBusDispatcherCommandBus implements CommandBus {
    private final CommandBus commandBus;
    private final EventBus eventBus;

    public EventBusDispatcherCommandBus(CommandBus commandBus, EventBus eventBus) {
        this.commandBus = commandBus;
        this.eventBus = eventBus;
    }

    public <R extends CommandResponse, C extends Command> R dispatch(C command) {
        R commandResponse = commandBus.dispatch(command);

        Command eventCommand = publishEvent(commandResponse);

        if (eventCommand != null) {
            var dispatch = this.dispatch(eventCommand);
            commandResponse.events().addAll(dispatch.events());
            return commandResponse;
        }

        return buildCommandResponseWishGeneratedEvents(commandResponse);
    }

    private <R extends CommandResponse> Command publishEvent(R commandResponse) {
        AtomicReference<Command> command = new AtomicReference<>();
        commandResponse.events().forEach(e -> command.set(eventBus.publish((Event) e)));
        return command.get();
    }


    private <R extends CommandResponse> R buildCommandResponseWishGeneratedEvents(R dispatchedCommandResponse) {
        dispatchedCommandResponse.events().addAll(eventBus.getPublishedEvents());
        return dispatchedCommandResponse;
    }
}
