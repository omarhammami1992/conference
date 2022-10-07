package com.soat.back.conference.command.domain;

import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreateConferenceUTest {
    private static final int GENERATED_CONFERENCE_ID = 42;
    private CreateConference createConference;

    private final ConferencePort conferencePort = conference -> GENERATED_CONFERENCE_ID;

    @BeforeEach
    void setUp() {

        createConference = new CreateConference(conferencePort);
    }

    @Test
    void execute_should_throw_exception_when_not_continuous_ranges() {
        // given
        final PriceRangeParams septemberPrice = new PriceRangeParams(150f, LocalDate.of(2022, 9, 1), LocalDate.of(2022, 9, 30));
        final PriceRangeParams novemberPrice = new PriceRangeParams(200f, LocalDate.of(2022, 11, 1), LocalDate.of(2022, 11, 30));
        float defaultPrice = 300f;

        ConferenceParams conferenceParams = new ConferenceParams(
              "devoxx",
              "link",
              LocalDate.of(2022, 12, 1),
              LocalDate.of(2022, 12, 3),
              defaultPrice,
              of(septemberPrice, novemberPrice),
              null,
              null
        );

        // when & then
        final Throwable throwable = catchThrowable(() -> createConference.execute(conferenceParams));

        // then
        assertThat(throwable).isInstanceOf(InvalidIntervalException.class);
    }

    @Test
    void execute_should_throw_exception_when_price_not_strictly_ascending() {
        // given
        final PriceRangeParams septemberPrice = new PriceRangeParams(250f, LocalDate.of(2022, 9, 1), LocalDate.of(2022, 9, 30));
        final PriceRangeParams octoberPrice = new PriceRangeParams(200f, LocalDate.of(2022, 10, 1), LocalDate.of(2022, 11, 30));
        float defaultPrice = 300f;

        ConferenceParams conferenceParams = new ConferenceParams(
                "devoxx",
                "link",
                LocalDate.of(2022, 12, 1),
                LocalDate.of(2022, 12, 3),
                defaultPrice,
                of(septemberPrice, octoberPrice),
                null,
                null
        );

        // when & then
        final Throwable throwable = catchThrowable(() -> createConference.execute(conferenceParams));

        // then
        assertThat(throwable).isInstanceOf(InvalidIntervalException.class);
    }
}