package com.soat.back.middleware.queries;

import com.soat.back.common.domain.cqrs.Query;
import com.soat.back.common.domain.cqrs.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueryBusFactory {

    public QueryBusFactory() {
    }

    protected List<QueryHandler<? extends Query, ? extends Object>> getQueryHandlers() {
        return List.of();
    }

    public QueryBus build() {
        List<QueryHandler<? extends Query, ? extends Object>> queryHandlers = getQueryHandlers();
        final QueryBusDispatcher queryBusDispatcher = new QueryBusDispatcher(queryHandlers);

        return new QueryBusLogger(queryBusDispatcher);
    }
}
