package com.soat.back.conference.command.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static java.util.List.of;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class CreateConferenceUTest {
    public static final int GENERATED_CONFERENCE_ID = 42;
    private CreateConference createConference;

    private ConferencePort conferencePort = conference -> GENERATED_CONFERENCE_ID;

    @BeforeEach
    void setUp() {

        createConference = new CreateConference(conferencePort);
    }

    @Test
    void execute_should_throw_exception_when_not_continuous_ranges() {
        // given
        final DateInterval september = new DateInterval(LocalDate.of(2022, 9, 1), LocalDate.of(2022, 9, 30));
        final PriceRange septemberPrice = new PriceRange(150f, september);
        final DateInterval november = new DateInterval(LocalDate.of(2022, 11, 1), LocalDate.of(2022, 11, 30));
        final PriceRange nevemberPrice = new PriceRange(200f, november);

        Conference conference = new Conference(
                "devoxx",
                "link",
                LocalDate.of(2022, 12,1),
                LocalDate.of(2022, 12,3),
                of(septemberPrice, nevemberPrice)
        );

        // when & then
        final Throwable throwable = catchThrowable(() -> createConference.execute(conference));

        // then
        assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
    }
}