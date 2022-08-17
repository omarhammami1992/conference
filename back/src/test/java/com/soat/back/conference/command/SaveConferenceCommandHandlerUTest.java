package com.soat.back.conference.command;

import com.soat.back.common.domain.ConferenceId;
import com.soat.back.conference.command.domain.ConferencePort;
import com.soat.back.conference.command.domain.ConferenceSavingException;
import com.soat.back.conference.command.domain.ConferenceToSave;
import com.soat.back.conference.event.SaveConferenceFailed;
import com.soat.back.conference.event.SaveConferenceSucceeded;
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
class SaveConferenceCommandHandlerUTest {

    private SaveConferenceCommandHandler saveConferenceCommandHandler;

    @Mock
    private ConferencePort conferencePort;

    @BeforeEach
    void setUp() {
        saveConferenceCommandHandler = new SaveConferenceCommandHandler(conferencePort);
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
            when(conferencePort.save(new ConferenceToSave(NAME, LINK, START_DATE, END_DATE))).thenReturn(savedConferenceId);

            // when
            final CommandResponse<Event> commandResponse = saveConferenceCommandHandler.handle(new SaveConferenceCommand(NAME, LINK, START_DATE, END_DATE));

            // then
            assertThat(commandResponse.events()).usingRecursiveFieldByFieldElementComparator().containsExactly(new SaveConferenceSucceeded(savedConferenceId));
        }

        @Test
        void should_return_SaveConferenceFailed_when_fail() throws ConferenceSavingException {
            // given
            when(conferencePort.save(new ConferenceToSave(NAME, LINK, START_DATE, END_DATE))).thenThrow(ConferenceSavingException.class);

            // when
            final CommandResponse<Event> commandResponse = saveConferenceCommandHandler.handle(new SaveConferenceCommand(NAME, LINK, START_DATE, END_DATE));

            // then
            assertThat(commandResponse.events()).usingRecursiveFieldByFieldElementComparator().containsExactly(new SaveConferenceFailed());
        }
    }

    @Nested
    class ListenTo {
        @Test
        void should_return_saveConferenceCommand_class() {
            // when
            final Class result = saveConferenceCommandHandler.listenTo();

            // then
            assertThat(result).isEqualTo(SaveConferenceCommand.class);
        }
    }
}