package com.soat.back.common.domain.cqrs;

public abstract class EventHandlerVoid<E> implements EventHandlerReturnVoid<E> {

    @Override
    public EventHandlerType getType() {
        return EventHandlerType.VOID;
    }
}
