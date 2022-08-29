package com.soat.back.conference.query;

import com.soat.back.common.domain.cqrs.QueryHandler;
import com.soat.back.common.domain.cqrs.QueryResponse;
import com.soat.back.conference.event.GetAllConferencesSucceeded;
import com.soat.back.conference.query.domain.Conference;
import com.soat.back.conference.query.domain.ConferencePort;

import java.util.List;

public class GetAllConferencesQueryHandler implements QueryHandler<GetAllConferencesQuery, QueryResponse<List<Conference>>> {
    private final ConferencePort conferencePort;

    public GetAllConferencesQueryHandler(ConferencePort conferencePort) {
        this.conferencePort = conferencePort;
    }

    @Override
    public QueryResponse<List<Conference>> handle(GetAllConferencesQuery query) {
        final List<Conference> conferences = conferencePort.findAll();
        return new QueryResponse<>(conferences, new GetAllConferencesSucceeded());
    }

    @Override
    public Class listenTo() {
        return GetAllConferencesQuery.class;
    }
}
