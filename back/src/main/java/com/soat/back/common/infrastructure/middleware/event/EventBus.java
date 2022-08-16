package com.soat.back.common.infrastructure.middleware.event;

import com.soat.back.common.domain.cqrs.Command;
import com.soat.back.common.domain.cqrs.Event;

import java.util.Set;

public interface EventBus {
    <C extends Command> C publish(Event event);

    void resetPublishedEvents();

    Set<Event> getPublishedEvents();
}
