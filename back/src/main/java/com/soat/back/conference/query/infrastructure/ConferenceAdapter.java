package com.soat.back.conference.query.infrastructure;

import com.soat.back.common.infrastructure.JpaConferenceRepository;
import com.soat.back.conference.query.domain.Conference;
import com.soat.back.conference.query.domain.ConferencePort;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ConferenceAdapterQuery")
public class ConferenceAdapter implements ConferencePort {
    private final JpaConferenceRepository jpaConferenceRepository;

    public ConferenceAdapter(JpaConferenceRepository jpaConferenceRepository) {
        this.jpaConferenceRepository = jpaConferenceRepository;
    }

    @Override
    public List<Conference> getAll() {

        return jpaConferenceRepository.findAll()
                .stream()
                .map(conf -> new ConferenceDto(conf.getId(),
                        conf.getName(),
                        conf.getStartDate(),
                        conf.getEndDate(),
                        conf.getPrice(),
                        conf.getPrice() == 0,
                        "Paris",
                        "France"))
                .map(conf -> (Conference) conf)
                .toList();
    }
}
