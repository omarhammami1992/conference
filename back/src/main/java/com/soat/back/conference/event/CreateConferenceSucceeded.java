package com.soat.back.conference.event;

import com.soat.back.common.domain.cqrs.Event;
import com.soat.back.common.domain.ConferenceId;

public record CreateConferenceSucceeded(ConferenceId conferenceId) implements Event {
}
