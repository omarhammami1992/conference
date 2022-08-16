package com.soat.back.middleware.event;

import com.soat.back.cqrs.Command;
import com.soat.back.cqrs.Event;

import java.util.Set;

public interface EventBus {
    <C extends Command> C publish(Event event);

    void resetPublishedEvents();

    Set<Event> getPublishedEvents();
}
