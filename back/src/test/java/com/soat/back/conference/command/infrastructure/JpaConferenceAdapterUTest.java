package com.soat.back.conference.command.infrastructure;

import com.soat.back.common.domain.ConferenceId;
import com.soat.back.common.infrastructure.JpaConference;
import com.soat.back.common.infrastructure.JpaConferenceRepository;
import com.soat.back.conference.command.domain.ConferencePort;
import com.soat.back.conference.command.domain.ConferenceSavingException;
import com.soat.back.conference.command.domain.ConferenceToSave;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.PersistenceException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JpaConferenceAdapterUTest {

    private ConferencePort conferencePort;

    @Mock
    private JpaConferenceRepository jpaConferenceRepository;

    @BeforeEach
    void setUp() {
        conferencePort = new JpaConferenceAdapter(jpaConferenceRepository);
    }

    @Nested
    class Save {
        @Test
        void should_save_conference_and_return_conferenceId() throws ConferenceSavingException {
            // given
            final String name = "DEVOXX";
            final String link = "https:www.devoxx";
            final LocalDate startDate = LocalDate.of(2022, 11, 1);
            final LocalDate endDate = LocalDate.of(2022, 11, 3);
            ConferenceToSave conferenceToSave = new ConferenceToSave(name, link, startDate, endDate);

            final int conferenceIdValue = 1;
            ArgumentCaptor<JpaConference> jpaConferenceArgumentCaptor = ArgumentCaptor.forClass(JpaConference.class);
            when(jpaConferenceRepository.save(jpaConferenceArgumentCaptor.capture())).thenReturn(new JpaConference(conferenceIdValue, name, link, startDate, endDate));

            // when
            final ConferenceId conferenceId = conferencePort.save(conferenceToSave);

            // then
            final ConferenceId expectedConferenceId = new ConferenceId(conferenceIdValue);
            assertThat(conferenceId).isEqualTo(expectedConferenceId);
            assertThat(jpaConferenceArgumentCaptor.getValue()).usingRecursiveComparison().isEqualTo(new JpaConference(null, name, link, startDate, endDate));
        }

        @Test
        void should_throw_ConferenceSavingException_when_error_while_saving() {
            // given
            final String name = "DEVOXX";
            final String link = "https:www.devoxx";
            final LocalDate startDate = LocalDate.of(2022, 11, 1);
            final LocalDate endDate = LocalDate.of(2022, 11, 3);
            ConferenceToSave conferenceToSave = new ConferenceToSave(name, link, startDate, endDate);

            when(jpaConferenceRepository.save(any(JpaConference.class))).thenThrow(PersistenceException.class);

            // when
            final Throwable throwable = catchThrowable(() -> conferencePort.save(conferenceToSave));

            // then
            assertThat(throwable).isInstanceOf(ConferenceSavingException.class);
        }
    }
}