package com.soat.back.conference.command;

import com.soat.back.conference.command.domain.ConferenceDto;
import com.soat.back.conference.command.domain.CreationResult;
import com.soat.back.conference.event.CreateConferenceFailed;
import com.soat.back.conference.event.CreateConferenceRejected;
import com.soat.back.conference.event.CreateConferenceSucceeded;
import com.soat.back.common.domain.cqrs.CommandHandler;
import com.soat.back.common.domain.cqrs.CommandResponse;
import com.soat.back.common.domain.cqrs.Event;
import com.soat.back.common.domain.ConferenceId;
import com.soat.back.conference.command.domain.ConferencePort;
import com.soat.back.conference.command.domain.ConferenceSavingException;

public class CreateConferenceCommandHandler implements CommandHandler<CreateConferenceCommand, CommandResponse<Event>> {
    private final ConferencePort conferencePort;

    public CreateConferenceCommandHandler(ConferencePort conferencePort) {
        this.conferencePort = conferencePort;
    }

    @Override
    public CommandResponse<Event> handle(CreateConferenceCommand command) {
        final CreationResult conference = ConferenceDto.create(command.name(), command.link(), command.startDate(), command.endDate());
        try {
            if (conference.event() instanceof CreateConferenceRejected event) {
                return new CommandResponse<>(event);
            }
            final ConferenceId conferenceId = conferencePort.save(conference.conferenceDto());
            return new CommandResponse<>(new CreateConferenceSucceeded(conferenceId));
        } catch (ConferenceSavingException e) {
            return new CommandResponse<>(new CreateConferenceFailed());
        }
    }

    @Override
    public Class listenTo() {
        return CreateConferenceCommand.class;
    }
}
