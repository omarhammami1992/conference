package com.soat.back.conference.query.domain;

import java.util.List;
import java.util.Optional;

public interface ConferencePort {
    List<Conference> getAll();

    Optional<Conference> getById(Integer id);
}
