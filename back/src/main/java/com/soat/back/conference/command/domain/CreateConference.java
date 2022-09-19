package com.soat.back.conference.command.domain;

import org.springframework.stereotype.Service;

@Service
public class CreateConference {

   private final ConferencePort conferencePort;

   public CreateConference(ConferencePort conferencePort) {
      this.conferencePort = conferencePort;
   }

   public Integer execute(Conference conference) {
      return conferencePort.save(conference);
   }

}
