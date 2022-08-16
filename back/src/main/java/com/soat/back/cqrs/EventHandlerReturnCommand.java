package com.soat.back.cqrs;


public interface EventHandlerReturnCommand<E> extends EventHandler {

    <C extends Command> C handle(E event);

}
