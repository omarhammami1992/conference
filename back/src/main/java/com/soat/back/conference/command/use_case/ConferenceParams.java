package com.soat.back.conference.command.use_case;

import com.soat.back.conference.command.domain.*;

import java.time.LocalDate;
import java.util.List;

public record ConferenceParams(String name,
                               String link,
                               LocalDate startDate,
                               LocalDate endDate,
                               Float price,
                               List<PriceRangeParams> priceRanges,
                               PriceGroupParams priceGroup,
                               List<PriceAttendingDaysParams> priceAttendingDays,
                               AddressParams address) {

    PriceGroup toPriceGroup() throws InvalidThresholdException {
        PriceGroup result = null;
        if (this.priceGroup != null) {
            result = this.priceGroup.convertToPriceGroup();
        }
        return result;
    }


    PriceRanges toPriceRanges() throws InvalidPricesException {
        return PriceRanges.create(this.priceRanges().stream()
                .map(priceRange -> new PriceRange(priceRange.price(),
                        new DateInterval(priceRange.startDate(), priceRange.endDate())))
                .toList());
    }

    PriceAttendingDays toPriceAttendingDays() throws InvalidAttendingDaysException {
        return PriceAttendingDays.create(this.priceAttendingDays().stream()
                .map(priceAttendingDay -> new PriceAttendingDay(priceAttendingDay.price(), priceAttendingDay.attendingDays()))
                .toList());
    }

    public Address toAddress() {
        return new Address(address.fullAddress(), address.city(), address.country(), address.latitude(), address.longitude());
    }
}
