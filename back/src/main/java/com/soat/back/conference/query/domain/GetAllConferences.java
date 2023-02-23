package com.soat.back.conference.query.domain;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllConferences {

    private final ConferencePort conferencePort;

    public GetAllConferences(ConferencePort conferencePort) {
        this.conferencePort = conferencePort;
    }

    public List<Conference> execute() {
        return conferencePort.getAll();
    }
}
