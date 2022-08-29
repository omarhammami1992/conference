package com.soat.back.conference.query.domain;

import java.util.List;

public interface ConferencePort {
    List<Conference> findAll();
}
