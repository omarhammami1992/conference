package com.soat.back.common.domain.cqrs;


public interface EventHandlerReturnCommand<E> extends EventHandler {

    <C extends Command> C handle(E event);

}
