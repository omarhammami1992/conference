package com.soat.back.conference.command.domain;

public record Address(String fullAddress,
                      String city,
                      String country,
                      String latitude,
                      String longitude
) {
}
