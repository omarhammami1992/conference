package com.soat.back.conference.command.infrastructure;

import com.soat.back.common.infrastructure.*;
import com.soat.back.conference.command.domain.Conference;
import com.soat.back.conference.command.domain.PriceAttendingDay;
import com.soat.back.conference.command.domain.PriceRange;
import com.soat.back.conference.command.use_case.ConferencePort;
import org.springframework.stereotype.Repository;

import java.util.List;

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
                conference.getName(),
                conference.getLink(),
                conference.getPrice(),
                conference.getStartDate(),
                conference.getEndDate(),
                conference.getCity(),
                conference.getCountry());
        List<JpaPriceRange> jpaPriceRanges = conference.getPriceRanges()
                .getValues()
                .stream()
                .map(priceRange -> toJpaPriceRange(jpaConference, priceRange))
                .toList();
        jpaConference.setPriceRanges(jpaPriceRanges);

        if (conference.getPriceGroup().price() != 0) {
            final var jpaPriceGroup = new JpaPriceGroup(conference.getPriceGroup().price(), conference.getPriceGroup().threshold(), jpaConference);
            jpaConference.setPriceGroup(jpaPriceGroup);
        }

        List<JpaPriceAttendingDay> jpaPriceAttendingDays = conference.getPriceAttendingDays()
                .getValues()
                .stream()
                .map(priceAttendingDay -> toJpaPriceAttendingDay(priceAttendingDay, jpaConference))
                .toList();
        jpaConference.setPriceAttendingDays(jpaPriceAttendingDays);

        return jpaConference;
    }

    private JpaPriceAttendingDay toJpaPriceAttendingDay(PriceAttendingDay priceAttendingDay, JpaConference conference) {
        return new JpaPriceAttendingDay(priceAttendingDay.price(), priceAttendingDay.attendingDay(), conference);
    }

    private JpaPriceRange toJpaPriceRange(JpaConference jpaConference, PriceRange priceRange) {
        return new JpaPriceRange(
                priceRange.price(),
                priceRange.dateInterval().startDate(),
                priceRange.dateInterval().endDate(), jpaConference
        );
    }
}
