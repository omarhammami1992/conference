package com.soat.back.conference.command;

import com.soat.back.common.domain.ConferenceId;
import com.soat.back.conference.command.domain.ConferencePort;
import com.soat.back.conference.command.domain.ConferenceSavingException;
import com.soat.back.conference.command.domain.Conference;
import com.soat.back.conference.event.CreateConferenceFailed;
import com.soat.back.conference.event.CreateConferenceRejected;
import com.soat.back.conference.event.CreateConferenceSucceeded;
import com.soat.back.common.domain.cqrs.CommandResponse;
import com.soat.back.common.domain.cqrs.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateConferenceCommandHandlerUTest {

    private CreateConferenceCommandHandler createConferenceCommandHandler;

    @Mock
    private ConferencePort conferencePort;

    @BeforeEach
    void setUp() {
        createConferenceCommandHandler = new CreateConferenceCommandHandler(conferencePort);
    }

    @Nested
    class Handle {

        private static final String NAME = "DEVOXX";
        private static final String LINK = "https:www.devoxx";
        private static final LocalDate START_DATE = LocalDate.of(2022, 11, 1);
        private static final LocalDate END_DATE = LocalDate.of(2022, 11, 3);

        @Test
        void should_return_SaveConferenceSucceeded_when_success() throws ConferenceSavingException {
            // given
            final ConferenceId savedConferenceId = new ConferenceId(12);
            when(conferencePort.save(new Conference(NAME, LINK, START_DATE, END_DATE))).thenReturn(savedConferenceId);

            // when
            final CommandResponse<Event> commandResponse = createConferenceCommandHandler.handle(new CreateConferenceCommand(NAME, LINK, START_DATE, END_DATE));

            // then
            assertThat(commandResponse.events()).usingRecursiveFieldByFieldElementComparator().containsExactly(new CreateConferenceSucceeded(savedConferenceId));
        }

        @Test
        void should_return_SaveConferenceFailed_when_fail() throws ConferenceSavingException {
            // given
            when(conferencePort.save(new Conference(NAME, LINK, START_DATE, END_DATE))).thenThrow(ConferenceSavingException.class);

            // when
            final CommandResponse<Event> commandResponse = createConferenceCommandHandler.handle(new CreateConferenceCommand(NAME, LINK, START_DATE, END_DATE));

            // then
            assertThat(commandResponse.events()).usingRecursiveFieldByFieldElementComparator().containsExactly(new CreateConferenceFailed());
        }

        @Test
        void should_return_SaveConferenceBadRequest_when_fail() throws ConferenceSavingException {
            // given
            final LocalDate START_DATE = LocalDate.of(2022, 11, 3);
            final LocalDate END_DATE = LocalDate.of(2022, 11, 1);

            // when
            final CommandResponse<Event> commandResponse = createConferenceCommandHandler.handle(new CreateConferenceCommand(NAME, LINK, START_DATE, END_DATE));

            // then
            assertThat(commandResponse.events()).usingRecursiveFieldByFieldElementComparator().containsExactly(new CreateConferenceRejected());
        }
    }

    @Nested
    class ListenTo {
        @Test
        void should_return_saveConferenceCommand_class() {
            // when
            final Class result = createConferenceCommandHandler.listenTo();

            // then
            assertThat(result).isEqualTo(CreateConferenceCommand.class);
        }
    }
}