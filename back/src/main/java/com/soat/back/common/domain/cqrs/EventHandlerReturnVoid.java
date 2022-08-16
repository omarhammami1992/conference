package com.soat.back.common.domain.cqrs;


public interface EventHandlerReturnVoid<E> extends EventHandler {

    void handle(E event);

}
