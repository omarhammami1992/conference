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
        final var conference = getConferenceById.execute(id);
        return conference.map(c -> new ResponseEntity<>(toConferenceJson(c), HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    private ConferenceJson toConferenceJson(Conference conference) {
        return new ConferenceJson(conference.getId(),
                conference.getName(),
                conference.getLink(),
                conference.getStartDate(),
                conference.getEndDate(),
                conference.getPrice(),
                conference.getIsOnline(),
                conference.getCity(),
                conference.getCountry());
    }
}
