package com.soat.back.cqrs;


public interface EventHandlerReturnVoid<E> extends EventHandler {

    void handle(E event);

}
