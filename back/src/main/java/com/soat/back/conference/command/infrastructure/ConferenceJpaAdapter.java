package com.soat.back.conference.command.infrastructure;

import java.util.List;

import com.soat.back.common.infrastructure.JpaPriceGroup;
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
      JpaConference jpaConference = toJpaConference(conferenceToSave);
      JpaConference conference = jpaConferenceRepository.save(jpaConference);
      return conference.getId();
   }

   //TODO: move to specific utility class add UTs
   private JpaConference toJpaConference(Conference conference) {
      JpaConference jpaConference = new JpaConference(
            conference.name(),
            conference.link(),
            conference.getPrice(),
            conference.startDate(),
            conference.endDate()
      );
      List<JpaPriceRange> jpaPriceRanges = conference.priceRanges()
            .stream()
            .map(priceRange -> toJpaPriceRange(jpaConference, priceRange))
            .toList();
      jpaConference.setPriceRanges(jpaPriceRanges);

      if (conference.getPriceGroup() != null) {
         final var jpaPriceGroup = new JpaPriceGroup(conference.getPriceGroup().price(), conference.getPriceGroup().threshold(), jpaConference);
         jpaConference.setPriceGroup(jpaPriceGroup);
      }

      return jpaConference;
   }

   private JpaPriceRange toJpaPriceRange(JpaConference jpaConference, PriceRange priceRange) {
      return new JpaPriceRange(
            priceRange.price(),
            priceRange.dateInterval().startDate(),
            priceRange.dateInterval().endDate(), jpaConference
      );
   }
}
