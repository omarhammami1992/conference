package com.soat.back.cqrs;


public interface EventHandlerReturnEvent<E> extends EventHandler {

    <Ev extends Event> Ev handle(E event);

}
