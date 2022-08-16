package com.soat.back.cqrs;

public interface EventHandler<T extends Event> {
    //Event handle(T event);

    Class listenTo();

    EventHandlerType getType();
}
