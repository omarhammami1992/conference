package com.soat.back.conference.query.infrastructure;

import com.soat.back.common.domain.ConferenceId;
import com.soat.back.common.infrastructure.JpaConference;
import com.soat.back.common.infrastructure.JpaConferenceRepository;
import com.soat.back.conference.query.domain.Conference;
import com.soat.back.conference.query.domain.ConferencePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.StreamSupport;

@Repository("Query.JpaConferenceAdapter")
public class JpaConferenceAdapter implements ConferencePort {

    @Autowired
    private JpaConferenceRepository jpaConferenceRepository;

    @Override
    public List<Conference> findAll() {
        final Iterable<JpaConference> jpaConferences = jpaConferenceRepository.findAll();
        return StreamSupport.stream(jpaConferences.spliterator(), false)
                .map(this::toConference)
                .toList();
    }

    private Conference toConference(JpaConference jpaConference) {
        return new Conference(
                new ConferenceId(
                        jpaConference.getId()),
                jpaConference.getName(),
                jpaConference.getLink(),
                jpaConference.getStartDate(),
                jpaConference.getEndDate()

        );
    }
}
