package com.soat.back.common.infrastructure.middleware.queries;

import com.soat.back.common.domain.cqrs.Query;
import com.soat.back.common.domain.cqrs.QueryHandler;
import com.soat.back.common.domain.cqrs.QueryResponse;

import java.util.List;
import java.util.Map;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toMap;

public class QueryBusDispatcher implements QueryBus {
    private final Map<Class, QueryHandler> queryHandlers;

    public QueryBusDispatcher(List<? extends QueryHandler> queryHandlers) {
        this.queryHandlers = queryHandlers.stream()
                .collect(toMap(QueryHandler::listenTo, queryHandler -> queryHandler));
    }

    public <R extends QueryResponse, C extends Query> R dispatch(C query) {
        QueryHandler<C, R> queryHandler = this.queryHandlers.get(query.getClass());
        return ofNullable(queryHandler).map(handler -> handler.handle(query)).orElseThrow(UnmatchedQueryHandlerException::new);
    }
}
