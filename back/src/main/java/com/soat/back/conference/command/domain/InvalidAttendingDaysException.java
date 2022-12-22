package com.soat.back.conference.command.domain;

public class InvalidAttendingDaysException extends Exception {
    public InvalidAttendingDaysException(String message) {
        super(message);
    }
}
