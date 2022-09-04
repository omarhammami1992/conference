package com.soat.back.common.infrastructure.middleware.queries;

import com.soat.back.common.domain.cqrs.Query;
import com.soat.back.common.domain.cqrs.QueryHandler;
import com.soat.back.conference.query.GetAllConferencesQueryHandler;
import com.soat.back.conference.query.domain.ConferencePort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueryBusFactory {

    private final ConferencePort conferencePort;
    public QueryBusFactory(ConferencePort conferencePort) {
        this.conferencePort = conferencePort;
    }

    protected List<QueryHandler<? extends Query, ? extends Object>> getQueryHandlers() {
        return List.of(
                new GetAllConferencesQueryHandler(conferencePort)
        );
    }

    public QueryBus build() {
        List<QueryHandler<? extends Query, ? extends Object>> queryHandlers = getQueryHandlers();
        final QueryBusDispatcher queryBusDispatcher = new QueryBusDispatcher(queryHandlers);

        return new QueryBusLogger(queryBusDispatcher);
    }
}
