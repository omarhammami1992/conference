package com.soat.back.conference.command.use_case;

import com.soat.back.conference.command.domain.*;

import java.time.LocalDate;
import java.util.List;

public record ConferenceParams(String name,
                               String link,
                               LocalDate startDate,
                               LocalDate endDate,
                               Float price,
                               List<PriceRangeParams> priceRangesParams,
                               PriceGroupParams priceGroupParams,
                               List<PriceAttendingDaysParams> priceAttendingDaysParams,
                               String city,
                               String country) {

    PriceGroup priceGroup() throws InvalidThresholdException {
        PriceGroup result = null;
        if (this.priceGroupParams != null) {
            result = this.priceGroupParams.convertToPriceGroup();
        }
        return result;
    }


    PriceRanges priceRanges() throws InvalidPricesException {
        return PriceRanges.create(this.priceRangesParams().stream()
                .map(priceRange -> new PriceRange(priceRange.price(),
                        new DateInterval(priceRange.startDate(), priceRange.endDate())))
                .toList());
    }

    PriceAttendingDays priceAttendingDays() throws InvalidAttendingDaysException {
        return PriceAttendingDays.create(this.priceAttendingDaysParams().stream()
                .map(priceAttendingDay -> new PriceAttendingDay(priceAttendingDay.price(), priceAttendingDay.attendingDays()))
                .toList());
    }
}
