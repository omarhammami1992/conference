package com.soat.back.common.infrastructure.middleware.command;

import com.soat.back.common.domain.cqrs.CommandHandler;
import com.soat.back.common.domain.cqrs.Event;
import com.soat.back.common.domain.cqrs.EventHandler;
import com.soat.back.common.infrastructure.middleware.event.EventBus;
import com.soat.back.common.infrastructure.middleware.event.EventBusFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommandBusFactory {

    public CommandBusFactory() {

    }

    protected List<CommandHandler> getCommandHandlers() {
        return List.of(
        );
    }

    protected List<EventHandler<? extends Event>> getEventHandlers() {
        return List.of();
    }

    public CommandBus build() {
        CommandBusDispatcher commandBusDispatcher = buildCommandBusDispatcher();

        EventBus eventBus = buildEventBus();

        CommandBusLogger commandBusLogger = new CommandBusLogger(commandBusDispatcher);

        return new EventBusDispatcherCommandBus(commandBusDispatcher, eventBus);
    }

    private EventBus buildEventBus() {
        EventBusFactory eventBusFactory = new EventBusFactory(getEventHandlers());
        return eventBusFactory.build();
    }

    private CommandBusDispatcher buildCommandBusDispatcher() {
        List<CommandHandler> commandHandlers = getCommandHandlers();
        return new CommandBusDispatcher(commandHandlers);
    }
}
