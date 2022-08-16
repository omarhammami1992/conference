package com.soat.back.common.infrastructure.middleware.queries;

import com.soat.back.common.domain.cqrs.Query;
import com.soat.back.common.domain.cqrs.QueryResponse;

public interface QueryBus {
    <R extends QueryResponse, C extends Query> R dispatch(C query);
}
