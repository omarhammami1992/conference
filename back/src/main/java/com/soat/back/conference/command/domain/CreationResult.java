package com.soat.back.conference.command.domain;

import com.soat.back.common.domain.cqrs.Event;

public record  CreationResult (Conference conference, Event event){

}