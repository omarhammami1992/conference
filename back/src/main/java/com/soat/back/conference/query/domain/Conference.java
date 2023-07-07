package com.soat.back.conference.query.domain;

import java.time.LocalDate;

public interface Conference {

    Integer getId();
    String getName();
    String getLink();
    LocalDate getStartDate();
    LocalDate getEndDate();
    Float getPrice();
    Boolean getIsOnline();
    Address getAddress();
}
