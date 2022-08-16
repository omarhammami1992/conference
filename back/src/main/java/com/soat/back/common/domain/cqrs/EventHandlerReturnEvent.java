package com.soat.back.common.domain.cqrs;


public interface EventHandlerReturnEvent<E> extends EventHandler {

    <Ev extends Event> Ev handle(E event);

}
