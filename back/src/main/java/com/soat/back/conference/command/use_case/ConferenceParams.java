package com.soat.back.conference.command.use_case;

import java.time.LocalDate;
import java.util.List;

public record ConferenceParams(String name,
                               String link,
                               LocalDate startDate,
                               LocalDate endDate,
                               Float price,
                               List<PriceRangeParams> priceRanges,
                               PriceGroupParams priceGroupParams,
                               List<PriceAttendingDaysParams> priceAttendingDaysParams,
                               String city,
                               String country) {
}
