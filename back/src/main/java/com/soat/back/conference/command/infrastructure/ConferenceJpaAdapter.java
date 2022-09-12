package com.soat.back.conference.command.infrastructure;

import org.springframework.stereotype.Repository;
import com.soat.back.common.infrastructure.JpaConference;
import com.soat.back.common.infrastructure.JpaConferenceRepository;
import com.soat.back.conference.command.domain.ConferencePort;
import com.soat.back.conference.command.domain.ConferenceToSave;

@Repository
public class ConferenceJpaAdapter implements ConferencePort {
   private final JpaConferenceRepository jpaConferenceRepository;

   public ConferenceJpaAdapter(JpaConferenceRepository jpaConferenceRepository) {
      this.jpaConferenceRepository = jpaConferenceRepository;
   }

   @Override
   public Integer save(ConferenceToSave conferenceToSave) {
      JpaConference jpaConference = new JpaConference(
            conferenceToSave.name(),
            conferenceToSave.link(),
            conferenceToSave.startDate(),
            conferenceToSave.endDate()
      );
      JpaConference conference = jpaConferenceRepository.save(jpaConference);
      return conference.getId();
   }
}
