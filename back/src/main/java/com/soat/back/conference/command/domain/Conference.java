package com.soat.back.conference.command.domain;

import java.time.LocalDate;

public interface Conference {
   String name();
   String link();
   LocalDate startDate();
   LocalDate endDate();
}
