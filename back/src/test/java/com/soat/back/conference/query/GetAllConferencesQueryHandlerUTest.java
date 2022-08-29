package com.soat.back.conference.query;

import com.soat.back.common.domain.ConferenceId;
import com.soat.back.common.domain.cqrs.QueryResponse;
import com.soat.back.conference.event.GetAllConferencesSucceeded;
import com.soat.back.conference.query.domain.Conference;
import com.soat.back.conference.query.domain.ConferencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static java.util.List.of;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllConferencesQueryHandlerUTest {

    private GetAllConferencesQueryHandler getAllConferencesQueryHandler;

    @Mock
    private ConferencePort conferencePort;

    @BeforeEach
    void setUp() {
        getAllConferencesQueryHandler = new GetAllConferencesQueryHandler(conferencePort);
    }

    @Nested
    class Handle {
        @Test
        void should_return_all_conferences() {
            // given
            final List<Conference> conferences = of(new Conference(new ConferenceId(1), "DEVOXX", "https:www.devoxx", LocalDate.of(2022, 1, 1), LocalDate.of(2022, 1, 3)));
            when(conferencePort.findAll()).thenReturn(conferences);

            // when
            final QueryResponse<List<Conference>> queryResponse = getAllConferencesQueryHandler.handle(new GetAllConferencesQuery());

            // then
            assertThat(queryResponse.value()).isEqualTo(conferences);
            assertThat(queryResponse.event()).isInstanceOf(GetAllConferencesSucceeded.class);
        }
    }

    @Nested
    class ListenTo {
        @Test
        void should_return_GetAllConferencesQuery_class() {
            // when
            final Class result = getAllConferencesQueryHandler.listenTo();

            // then
            assertThat(result).isEqualTo(GetAllConferencesQuery.class);
        }
    }
}