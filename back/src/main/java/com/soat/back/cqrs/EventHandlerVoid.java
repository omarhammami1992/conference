package com.soat.back.cqrs;

public abstract class EventHandlerVoid<E> implements EventHandlerReturnVoid<E> {

    @Override
    public EventHandlerType getType() {
        return EventHandlerType.VOID;
    }
}
