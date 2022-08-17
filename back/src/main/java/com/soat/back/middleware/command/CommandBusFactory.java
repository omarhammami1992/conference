package com.soat.back.middleware.command;

import com.soat.back.common.domain.cqrs.CommandHandler;
import com.soat.back.common.domain.cqrs.Event;
import com.soat.back.common.domain.cqrs.EventHandler;
import com.soat.back.conference.command.SaveConferenceCommandHandler;
import com.soat.back.conference.command.domain.ConferencePort;
import com.soat.back.middleware.event.EventBus;
import com.soat.back.middleware.event.EventBusFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommandBusFactory {

    private final ConferencePort conferencePort;

    public CommandBusFactory(ConferencePort conferencePort) {
        this.conferencePort = conferencePort;
    }

    protected List<CommandHandler> getCommandHandlers() {
        return List.of(
                new SaveConferenceCommandHandler(conferencePort)
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
