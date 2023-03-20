package com.soat.back.conference.command.use_case;

import java.time.LocalDate;

public record PriceRangeParams(Float price, LocalDate startDate, LocalDate endDate) {
}
