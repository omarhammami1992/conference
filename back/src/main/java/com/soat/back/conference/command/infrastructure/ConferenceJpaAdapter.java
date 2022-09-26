package com.soat.back.conference.command.infrastructure;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.soat.back.common.infrastructure.JpaConference;
import com.soat.back.common.infrastructure.JpaConferenceRepository;
import com.soat.back.common.infrastructure.JpaPriceRange;
import com.soat.back.conference.command.domain.Conference;
import com.soat.back.conference.command.domain.ConferencePort;
import com.soat.back.conference.command.domain.PriceRange;

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
      JpaConference jpaConference = new JpaConference(
            conferenceToSave.name(),
            conferenceToSave.link(),
            conferenceToSave.startDate(),
            conferenceToSave.endDate()
      );
      List<JpaPriceRange> jpaPriceRanges = conferenceToSave.priceRanges()
            .stream()
            .map(priceRange -> toJpaPriceRange(jpaConference, priceRange))
            .toList();
      jpaConference.setPriceRanges(jpaPriceRanges);
      return jpaConference;
   }

   private static JpaPriceRange toJpaPriceRange(JpaConference jpaConference, PriceRange priceRange) {
      return new JpaPriceRange(
            priceRange.price(),
            priceRange.dateInterval().startDate(),
            priceRange.dateInterval().endDate(), jpaConference
      );
   }
}
