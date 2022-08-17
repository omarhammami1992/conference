package com.soat.back.conference.command.domain;

import com.soat.back.common.domain.ConferenceId;

public interface ConferencePort {
    ConferenceId save(ConferenceToSave conferenceToSave) throws ConferenceSavingException;
}
