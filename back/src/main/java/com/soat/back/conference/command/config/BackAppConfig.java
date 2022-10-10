package com.soat.back.conference.command.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.soat.back.conference.command.domain.ConferencePort;
import com.soat.back.conference.command.domain.CreateConference;

@Configuration
public class BackAppConfig {

   @Bean
   public CreateConference createConference(ConferencePort conferencePort) {
      return new CreateConference(conferencePort);
   }
}
