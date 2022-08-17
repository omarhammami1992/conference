package com.soat.back.middleware.event;


import com.soat.back.common.domain.cqrs.Event;
import com.soat.back.common.domain.cqrs.EventHandler;

import java.util.List;

public class EventBusFactory {
    private final List<EventHandler<? extends Event>> eventHandlers;

    public EventBusFactory(List<EventHandler<? extends Event>> eventHandlers) {
        this.eventHandlers = eventHandlers;
    }

    public EventBus build() {
        EventBus eventBusDispatcher = new EventBusDispatcher(eventHandlers);

        return new EventBusLogger(eventBusDispatcher);
    }

}
