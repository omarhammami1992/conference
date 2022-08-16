package com.soat.back.common.domain.cqrs;

public interface QueryHandler<Q extends Query, R extends QueryResponse> {

    R handle(Q query);

    Class listenTo();
}
