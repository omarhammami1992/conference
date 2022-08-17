package com.soat.back.conference.command;

import com.soat.back.conference.event.SaveConferenceFailed;
import com.soat.back.conference.event.SaveConferenceSucceeded;
import com.soat.back.common.domain.cqrs.CommandHandler;
import com.soat.back.common.domain.cqrs.CommandResponse;
import com.soat.back.common.domain.cqrs.Event;
import com.soat.back.common.domain.ConferenceId;
import com.soat.back.conference.command.domain.ConferencePort;
import com.soat.back.conference.command.domain.ConferenceSavingException;
import com.soat.back.conference.command.domain.ConferenceToSave;

public class SaveConferenceCommandHandler implements CommandHandler<SaveConferenceCommand, CommandResponse<Event>> {
    private final ConferencePort conferencePort;

    public SaveConferenceCommandHandler(ConferencePort conferencePort) {
        this.conferencePort = conferencePort;
    }

    @Override
    public CommandResponse<Event> handle(SaveConferenceCommand command) {
        final ConferenceToSave conferenceToSave = new ConferenceToSave(command.name(), command.link(), command.startDate(), command.endDate());
        try {
            final ConferenceId conferenceId = conferencePort.save(conferenceToSave);
            return new CommandResponse<>(new SaveConferenceSucceeded(conferenceId));
        } catch (ConferenceSavingException e) {
            return new CommandResponse<>(new SaveConferenceFailed());
        }
    }

    @Override
    public Class listenTo() {
        return SaveConferenceCommand.class;
    }
}
