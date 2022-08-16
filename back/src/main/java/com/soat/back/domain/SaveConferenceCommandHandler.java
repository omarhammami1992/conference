package com.soat.back.domain;

import com.soat.back.cqrs.CommandHandler;
import com.soat.back.cqrs.CommandResponse;
import com.soat.back.cqrs.Event;

public class SaveConferenceCommandHandler implements CommandHandler<SaveConferenceCommand, CommandResponse<Event>> {
    @Override
    public CommandResponse<Event> handle(SaveConferenceCommand command) {
        return null;
    }

    @Override
    public Class listenTo() {
        return null;
    }
}
