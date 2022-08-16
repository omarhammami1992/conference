package com.soat.back.common.application;

import com.soat.back.common.infrastructure.middleware.queries.QueryBus;
import com.soat.back.common.infrastructure.middleware.queries.QueryBusFactory;


public abstract class QueryController {
    private QueryBus queryBus;
    private final QueryBusFactory queryBusFactory;

    public QueryController(QueryBusFactory queryBusFactory) {
        this.queryBusFactory = queryBusFactory;
    }

    protected QueryBus getQueryBus() {
        if (queryBus == null) {
            this.queryBus = queryBusFactory.build();
        }
        return queryBus;
    }

}
