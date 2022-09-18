package com.soat.back.conference.command.application;

import java.util.List;

public record ConferenceJson(String name, String link, String startDate, String endDate,
                             List<PricingRangeJson> pricingRanges) {
}
