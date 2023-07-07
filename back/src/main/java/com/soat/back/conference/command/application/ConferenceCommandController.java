package com.soat.back.conference.command.application;

import com.soat.back.conference.command.domain.InvalidAttendingDaysException;
import com.soat.back.conference.command.domain.InvalidIntervalException;
import com.soat.back.conference.command.domain.InvalidPricesException;
import com.soat.back.conference.command.domain.InvalidThresholdException;
import com.soat.back.conference.command.use_case.ConferenceParams;
import com.soat.back.conference.command.use_case.CreateConference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/conference")
public class ConferenceCommandController {

    private final CreateConference createConference;

    public ConferenceCommandController(CreateConference createConference) {
        this.createConference = createConference;
    }

    @PostMapping
    public ResponseEntity<Integer> save(@RequestBody ConferenceParams conferenceParams) throws InvalidIntervalException, InvalidPricesException, InvalidThresholdException, InvalidAttendingDaysException {
        Integer id = createConference.execute(conferenceParams);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }
}
