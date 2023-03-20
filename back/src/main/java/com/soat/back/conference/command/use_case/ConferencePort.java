package com.soat.back.conference.command.use_case;

import com.soat.back.conference.command.domain.Conference;

public interface ConferencePort {

   Integer save(Conference conference);
}
