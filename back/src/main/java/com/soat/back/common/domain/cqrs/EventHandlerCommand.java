package com.soat.back.common.domain.cqrs;

public abstract class EventHandlerCommand<E> implements EventHandlerReturnCommand<E> {

    @Override
    public EventHandlerType getType() {
        return EventHandlerType.COMMAND;
    }
}
