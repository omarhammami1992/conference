package com.soat.back.conference.query.application;

import com.soat.back.conference.query.domain.Conference;
import com.soat.back.conference.query.domain.GetAllConferences;
import com.soat.back.conference.query.domain.GetConferenceById;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("conference")
public class ConferenceQueryController {

    private final GetAllConferences getAllConferences;
    private final GetConferenceById getConferenceById;

    public ConferenceQueryController(GetAllConferences getAllConferences, GetConferenceById getConferenceById) {
        this.getAllConferences = getAllConferences;
        this.getConferenceById = getConferenceById;
    }

    @GetMapping
    public ResponseEntity<List<ConferenceJson>> getAll() {

        var conferences = getAllConferences.execute()
                .stream()
                .map(this::toConferenceJson)
                .toList();
        return new ResponseEntity<>(conferences, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<ConferenceJson> getById(@PathVariable("id") Integer id){
        final Conference conference = getConferenceById.execute(id);
        final ConferenceJson conferenceJson = toConferenceJson(conference);
        return new ResponseEntity<>(conferenceJson, HttpStatus.OK);
    }

    private ConferenceJson toConferenceJson(Conference conference) {
        return new ConferenceJson(conference.id(),
                conference.name(),
                conference.link(),
                conference.startDate(),
                conference.endDate(),
                conference.fullPrice(),
                conference.isOnline(),
                conference.city(),
                conference.country());
    }
}
