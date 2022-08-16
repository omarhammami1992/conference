package com.soat.back.cqrs;

public interface QueryHandler<Q extends Query, R extends QueryResponse> {

    R handle(Q query);

    Class listenTo();
}
