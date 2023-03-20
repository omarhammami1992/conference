package com.soat.back.conference.command.application.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.soat.back.conference.command.use_case.ConferencePort;
import com.soat.back.conference.command.use_case.CreateConference;

@Configuration
public class BackAppConfig {

   @Bean
   public CreateConference createConference(ConferencePort conferencePort) {
      return new CreateConference(conferencePort);
   }
}
