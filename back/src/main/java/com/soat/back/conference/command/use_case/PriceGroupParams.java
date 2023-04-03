package com.soat.back.conference.command.use_case;

import com.soat.back.conference.command.domain.InvalidThresholdException;
import com.soat.back.conference.command.domain.PriceGroup;

public record PriceGroupParams(Float priceGroup, Integer participantsThreshold) {
    PriceGroup convertToPriceGroup() throws InvalidThresholdException {
        return PriceGroup.create(this.priceGroup(), this.participantsThreshold());
    }
}
