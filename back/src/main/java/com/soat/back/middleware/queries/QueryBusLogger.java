package com.soat.back.middleware.queries;

import com.soat.back.common.domain.cqrs.Query;
import com.soat.back.common.domain.cqrs.QueryResponse;

public class QueryBusLogger implements QueryBus {

    private final QueryBus queryBus;

    public QueryBusLogger(QueryBus queryBus) {
        this.queryBus = queryBus;
    }

    @Override
    public <R extends QueryResponse, C extends Query> R dispatch(C query) {
        final R queryResponse = this.queryBus.dispatch(query);
        System.out.println(query.toString());
        return queryResponse;
    }
}
