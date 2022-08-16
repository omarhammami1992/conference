package com.soat.back.middleware.queries;

import com.soat.back.cqrs.Query;
import com.soat.back.cqrs.QueryResponse;

public interface QueryBus {
    <R extends QueryResponse, C extends Query> R dispatch(C query);
}
