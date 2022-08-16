package com.soat.back.common.domain.cqrs;


public interface CommandHandler<C extends Command, R extends CommandResponse> {

    R handle(C command);

    Class listenTo();
}
