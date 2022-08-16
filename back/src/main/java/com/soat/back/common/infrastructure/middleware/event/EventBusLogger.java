package com.soat.back.common.infrastructure.middleware.event;

import com.soat.back.common.domain.cqrs.Command;
import com.soat.back.common.domain.cqrs.Event;

import java.util.Set;

public class EventBusLogger implements EventBus {

    private final EventBus eventBus;

    public EventBusLogger(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public Command publish(Event event) {
        Command command = this.eventBus.publish(event);
        if (event != null) {
            System.out.println(event);
        }
        return command;
    }

    @Override
    public void resetPublishedEvents() {
        eventBus.resetPublishedEvents();
    }

    @Override
    public Set<Event> getPublishedEvents() {
        return eventBus.getPublishedEvents();
    }
}
