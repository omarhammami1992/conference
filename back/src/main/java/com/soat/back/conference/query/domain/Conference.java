package com.soat.back.conference.query.domain;

import java.time.LocalDate;

public interface Conference {

    Integer id();

    String name();

    LocalDate startDate();

    LocalDate endDate();

    float fullPrice();

    Boolean isOnline();

    String city();

    String country();
}
