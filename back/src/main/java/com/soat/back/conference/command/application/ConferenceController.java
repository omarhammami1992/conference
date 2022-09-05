package com.soat.back.conference.command.application;

import com.soat.back.common.application.CommandController;
import com.soat.back.common.domain.cqrs.CommandResponse;
import com.soat.back.common.domain.cqrs.Event;
import com.soat.back.conference.command.CreateConferenceCommand;
import com.soat.back.conference.event.CreateConferenceSucceeded;
import com.soat.back.common.infrastructure.middleware.command.CommandBusFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/conference")
public class ConferenceController extends CommandController {

    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public ConferenceController(CommandBusFactory commandBusFactory) {
        super(commandBusFactory);
        commandBusFactory.build();
    }

    @PostMapping("")
    public ResponseEntity<Integer> save(@RequestBody ConferenceToSaveJson conferenceToSaveJson) {
        final CreateConferenceCommand createConferenceCommand = new CreateConferenceCommand(
                conferenceToSaveJson.name(),
                conferenceToSaveJson.link(),
                LocalDate.parse(conferenceToSaveJson.startDate(), DATE_TIME_FORMATTER),
                LocalDate.parse(conferenceToSaveJson.endDate(), DATE_TIME_FORMATTER)
        );
        CommandResponse<Event> commandResponse = getCommandBus().dispatch(createConferenceCommand);

        return commandResponse.findFirst(CreateConferenceSucceeded.class)
                .map(event ->  {
                    final CreateConferenceSucceeded createConferenceSucceeded = (CreateConferenceSucceeded) event;
                    return new ResponseEntity(createConferenceSucceeded.conferenceId().value(), HttpStatus.CREATED);
                })
                .orElse(new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR));

    }
}
