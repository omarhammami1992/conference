package com.soat.back.common.domain.cqrs;

public abstract class EventHandlerEvent<E> implements EventHandlerReturnEvent<E> {

    @Override
    public EventHandlerType getType() {
        return EventHandlerType.EVENT;
    }
}
