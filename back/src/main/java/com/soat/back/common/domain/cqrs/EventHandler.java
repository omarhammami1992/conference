package com.soat.back.common.domain.cqrs;

public interface EventHandler<T extends Event> {
    //Event handle(T event);

    Class listenTo();

    EventHandlerType getType();
}
