package com.soat.back.conference.command.application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.soat.back.conference.command.domain.CreateConference;
import com.soat.back.conference.command.domain.ConferenceToSave;

@RestController
@RequestMapping("/conference")
public class ConferenceController{

    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private final CreateConference createConference;

    public ConferenceController(CreateConference createConference) {
        this.createConference = createConference;
    }

    @PostMapping("")
    public ResponseEntity<Integer> save(@RequestBody ConferenceToSaveJson conferenceToSaveJson) {
        ConferenceToSave buildConferenceToSave = buildCreateConferenceCommand(conferenceToSaveJson);
        Integer id = createConference.execute(buildConferenceToSave);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    private ConferenceToSave buildCreateConferenceCommand(ConferenceToSaveJson conferenceToSaveJson) {
        return new ConferenceToSave(conferenceToSaveJson.name(),
              conferenceToSaveJson.link(),
              LocalDate.parse(conferenceToSaveJson.startDate(), DATE_TIME_FORMATTER),
              LocalDate.parse(conferenceToSaveJson.endDate(), DATE_TIME_FORMATTER)
        );
    }
}
