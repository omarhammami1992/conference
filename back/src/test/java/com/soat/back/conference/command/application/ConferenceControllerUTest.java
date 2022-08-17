package com.soat.back.conference.command.application;

import com.soat.back.common.domain.cqrs.CommandResponse;
import com.soat.back.common.domain.ConferenceId;
import com.soat.back.conference.command.SaveConferenceCommand;
import com.soat.back.conference.event.SaveConferenceFailed;
import com.soat.back.conference.event.SaveConferenceSucceeded;
import com.soat.back.middleware.command.CommandBus;
import com.soat.back.middleware.command.CommandBusFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConferenceControllerUTest {

    private ConferenceController conferenceController;

    @Mock
    private CommandBusFactory commandBusFactory;

    private CommandBus commandBus;

    @BeforeEach
    void setUp() {
        commandBus = mock(CommandBus.class);
        when(commandBusFactory.build()).thenReturn(commandBus);
        conferenceController = new ConferenceController(commandBusFactory);
    }

    @Nested
    class Save {
        @Test
        void should_return_id_of_saved_conference_and_http_status_201_when_saving_succeeded() {
            // given
            final int expectedConferenceId = 1;
            final SaveConferenceSucceeded saveConferenceSucceeded = new SaveConferenceSucceeded(new ConferenceId(expectedConferenceId));
            when(commandBus.dispatch(any(SaveConferenceCommand.class))).thenReturn(new CommandResponse<>(saveConferenceSucceeded));

            // when
            final ResponseEntity<Integer> responseEntity = conferenceController.save(new ConferenceToSaveJson("DEVOXX", "https:www.devoxx", "01-01-2022", "03-01-2022"));

            // then
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            assertThat(responseEntity.getBody()).isEqualTo(expectedConferenceId);
        }

        @Test
        void should_return_http_status_500_when_failed() {
            // given
            final SaveConferenceFailed saveConferenceSucceeded = new SaveConferenceFailed();
            when(commandBus.dispatch(any(SaveConferenceCommand.class))).thenReturn(new CommandResponse<>(saveConferenceSucceeded));

            // when
            final ResponseEntity<Integer> responseEntity = conferenceController.save(new ConferenceToSaveJson("DEVOXX", "https:www.devoxx", "01-01-2022", "03-01-2022"));

            // then
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}