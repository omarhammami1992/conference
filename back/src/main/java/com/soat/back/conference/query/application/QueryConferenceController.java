package com.soat.back.conference.query.application;

import com.soat.back.conference.query.domain.GetAllConferences;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("conference")
public class QueryConferenceController {

    private final GetAllConferences getAllConferences;
    private final GetConferenceById getConferenceById;

    public QueryConferenceController(GetAllConferences getAllConferences) {
        this.getAllConferences = getAllConferences;
    }

    @GetMapping
    public ResponseEntity<List<ConferenceJson>> getAll() {

        var conferences = getAllConferences.execute()
                .stream()
                .map(c -> new ConferenceJson(c.id(),
                        c.name(),
                        c.startDate(),
                        c.endDate(),
                        c.fullPrice(),
                        c.isOnline(),
                        c.city(),
                        c.country()))
                .toList();
        return new ResponseEntity<>(conferences, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<ConferenceJson> getById(@PathVariable("id") Integer id){
        return getConferenceById(id);
    }
}
