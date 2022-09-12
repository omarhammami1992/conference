package com.soat.back.conference.command.application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.soat.back.common.infrastructure.JpaConference;
import com.soat.back.common.infrastructure.JpaConferenceRepository;

@RestController
@RequestMapping("/conference")
public class ConferenceController{

    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

//    @Autowired
    private final JpaConferenceRepository jpaConferenceRepository;

    public ConferenceController(JpaConferenceRepository jpaConferenceRepository) {
        this.jpaConferenceRepository = jpaConferenceRepository;
    }

    @PostMapping("")
    public ResponseEntity<Integer> save(@RequestBody ConferenceToSaveJson conferenceToSaveJson) {
        JpaConference jpaConference = new JpaConference(conferenceToSaveJson.name(),
              conferenceToSaveJson.link(),
              LocalDate.parse(conferenceToSaveJson.startDate(), DATE_TIME_FORMATTER),
              LocalDate.parse(conferenceToSaveJson.endDate(), DATE_TIME_FORMATTER)
        );
        JpaConference savedJpaConference = jpaConferenceRepository.save(jpaConference);
        return new ResponseEntity<>(savedJpaConference.getId(), HttpStatus.CREATED);
    }
}
