package com.soat.back.conference.query.domain;


import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetConferenceById {

    private final ConferencePort conferencePort;

    public GetConferenceById(ConferencePort conferencePort) {
        this.conferencePort = conferencePort;
    }

    public Optional<Conference> execute(Integer id) {
        return conferencePort.getById(id);
    }
}
