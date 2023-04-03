package com.soat.back.conference.command.use_case;

import com.soat.back.conference.command.domain.InvalidThresholdException;
import com.soat.back.conference.command.domain.PriceGroup;

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

    PriceGroup priceGroup() throws InvalidThresholdException {
        PriceGroup result = null;
        if (this.priceGroupParams != null) {
            result = this.priceGroupParams.convertToPriceGroup();
        }
        return result;
    }
}
