package com.soat.back.conference.command.infrastructure;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.soat.back.common.infrastructure.JpaConference;
import com.soat.back.common.infrastructure.JpaConferenceRepository;
import com.soat.back.common.infrastructure.JpaPricingRange;
import com.soat.back.conference.command.domain.ConferencePort;
import com.soat.back.conference.command.domain.Conference;
import com.soat.back.conference.command.domain.PricingRange;

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

   private static JpaConference convertToJpaConference(Conference conference) {
      final JpaConference jpaConference = new JpaConference(
              conference.name(),
              conference.link(),
              conference.startDate(),
              conference.endDate()
      );
      List<JpaPricingRange> pricingRanges = conference.pricingRanges().stream()
            .map(pricingRange -> convertToPricingRange(pricingRange, jpaConference))
            .toList();
      jpaConference.setPricingRanges(pricingRanges);
      return jpaConference;
   }

   private static JpaPricingRange convertToPricingRange(PricingRange pricingRange, JpaConference conference) {
      return new JpaPricingRange(pricingRange.startDate(), pricingRange.endDate(), pricingRange.price(), conference);
   }
}
