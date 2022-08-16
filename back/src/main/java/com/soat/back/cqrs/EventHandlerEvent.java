package com.soat.back.cqrs;

public abstract class EventHandlerEvent<E> implements EventHandlerReturnEvent<E> {

    @Override
    public EventHandlerType getType() {
        return EventHandlerType.EVENT;
    }
}
