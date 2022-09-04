package com.soat.back.conference.query.application;

import com.soat.back.common.application.QueryController;
import com.soat.back.common.domain.cqrs.QueryResponse;
import com.soat.back.conference.event.GetAllConferencesSucceeded;
import com.soat.back.conference.query.GetAllConferencesQuery;
import com.soat.back.conference.query.domain.Conference;
import com.soat.back.common.infrastructure.middleware.queries.QueryBusFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("Query.ConferenceController")
@RequestMapping("/conference")
public class ConferenceController extends QueryController {
    public ConferenceController(QueryBusFactory queryBusFactory) {
        super(queryBusFactory);
        queryBusFactory.build();
    }

    @GetMapping("")
    ResponseEntity<List<ConferenceJson>> getAll() {
        final QueryResponse<List<Conference>> queryResponse = getQueryBus().dispatch(new GetAllConferencesQuery());
        if (queryResponse.event() instanceof GetAllConferencesSucceeded) {
            final List<ConferenceJson> conferenceJsons = queryResponse.value()
                    .stream().map(ConferenceController::toConferenceJson)
                    .toList();
            return new ResponseEntity<>(conferenceJsons, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static ConferenceJson toConferenceJson(Conference conference) {
        return new ConferenceJson(conference.id().value(),
                conference.name(),
                conference.link(),
                conference.startDate(),
                conference.endDate());
    }
}
