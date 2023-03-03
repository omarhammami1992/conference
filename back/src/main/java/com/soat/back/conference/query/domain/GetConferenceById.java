package com.soat.back.conference.query.domain;


import org.springframework.stereotype.Service;

@Service
public class GetConferenceById {

    private final ConferencePort conferencePort;

    public GetConferenceById(ConferencePort conferencePort) {
        this.conferencePort = conferencePort;
    }

    public Conference execute(Integer id) {
        // TODO gèrer l'optional
        return conferencePort.getById(id).get();
    }
}
