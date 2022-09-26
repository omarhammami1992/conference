package com.soat.back.conference.command.domain;

import java.time.LocalDate;

public record PriceRange(Float price, DateInterval dateInterval) {
}
