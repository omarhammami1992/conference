package com.soat.back.conference.command.application;

import java.util.List;

public record ConferenceJson(String name, String link, String startDate, String endDate, float price,
                             List<PriceRangeJson> priceRanges, PriceGroupJson priceGroup, List<PriceAttendingDaysJson> attendingDays) {
}
