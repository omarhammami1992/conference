package com.soat.back.conference.command.infrastructure;

import org.springframework.stereotype.Repository;
import com.soat.back.common.infrastructure.JpaConference;
import com.soat.back.common.infrastructure.JpaConferenceRepository;
import com.soat.back.conference.command.domain.ConferencePort;
import com.soat.back.conference.command.domain.Conference;

@Repository
public class ConferenceJpaAdapter implements ConferencePort {
   private final JpaConferenceRepository jpaConferenceRepository;

   public ConferenceJpaAdapter(JpaConferenceRepository jpaConferenceRepository) {
      this.jpaConferenceRepository = jpaConferenceRepository;
   }

   @Override
   public Integer save(Conference conferenceToSave) {
      JpaConference jpaConference = convertToJpaConference(conferenceToSave);
      JpaConference conference = jpaConferenceRepository.save(jpaConference);
      return conference.getId();
   }

   private static JpaConference convertToJpaConference(Conference conferenceToSave) {
      return new JpaConference(
            conferenceToSave.name(),
            conferenceToSave.link(),
            conferenceToSave.startDate(),
            conferenceToSave.endDate()
      );
   }
}
