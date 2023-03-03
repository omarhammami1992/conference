package com.soat.back.conference.query.domain;

import java.time.LocalDate;

public interface Conference {

    Integer id();

    String name();
    String link();

    LocalDate startDate();

    LocalDate endDate();

    float fullPrice();

    Boolean isOnline();

    String city();

    String country();
}
