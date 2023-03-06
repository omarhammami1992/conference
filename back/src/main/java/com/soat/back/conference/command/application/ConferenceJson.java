package com.soat.back.conference.command.application;

import java.time.LocalDate;
import java.util.List;

public record ConferenceJson(String name, String link, LocalDate startDate, LocalDate endDate, float price,
                             List<PriceRangeJson> priceRanges, PriceGroupJson priceGroup, List<PriceAttendingDaysJson> attendingDays,
                             String city, String country) {
}
