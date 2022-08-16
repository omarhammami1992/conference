package com.soat.back.cqrs;

public abstract class EventHandlerCommand<E> implements EventHandlerReturnCommand<E> {

    @Override
    public EventHandlerType getType() {
        return EventHandlerType.COMMAND;
    }
}
