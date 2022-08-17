package com.soat.back.conference.command.infrastructure;

import com.soat.back.common.domain.ConferenceId;
import com.soat.back.common.infrastructure.JpaConference;
import com.soat.back.common.infrastructure.JpaConferenceRepository;
import com.soat.back.conference.command.domain.ConferencePort;
import com.soat.back.conference.command.domain.ConferenceSavingException;
import com.soat.back.conference.command.domain.ConferenceToSave;
import org.springframework.stereotype.Repository;

@Repository
public class JpaConferenceAdapter implements ConferencePort {
    private final JpaConferenceRepository jpaConferenceRepository;

    public JpaConferenceAdapter(JpaConferenceRepository jpaConferenceRepository) {
        this.jpaConferenceRepository = jpaConferenceRepository;
    }

    @Override
    public ConferenceId save(ConferenceToSave conferenceToSave) throws ConferenceSavingException {
        try {
            JpaConference jpaConference = new JpaConference(null, conferenceToSave.name(), conferenceToSave.link(), conferenceToSave.startDate(), conferenceToSave.endDate());
            final JpaConference savedJpaConference = jpaConferenceRepository.save(jpaConference);
            return new ConferenceId(savedJpaConference.getId());
        } catch (Throwable throwable) {
            throw new ConferenceSavingException(throwable);
        }
    }
}
