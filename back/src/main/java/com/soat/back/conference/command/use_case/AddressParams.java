package com.soat.back.conference.command.use_case;

public record AddressParams(String fullAddress,
                            String city,
                            String country,
                            String latitude,
                            String longitude) {
}
