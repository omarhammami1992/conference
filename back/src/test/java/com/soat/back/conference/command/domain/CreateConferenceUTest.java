package com.soat.back.conference.command.domain;

import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateConferenceUTest {
    private static final int GENERATED_CONFERENCE_ID = 42;
    private CreateConference createConference;

    private final ConferencePort conferencePort = conference -> GENERATED_CONFERENCE_ID;

    @BeforeEach
    void setUp() {

        createConference = new CreateConference(conferencePort);
    }

    @Nested
    class CreateWithPriceRanges {

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
                    null, of()
            );

            // when
            final Throwable throwable = catchThrowable(() -> createConference.execute(conferenceParams));

            // then
            assertThat(throwable).isInstanceOf(InvalidIntervalException.class);
        }

        @Test
        void execute_should_throw_exception_when_price_not_ascending() {
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
                    of()
            );

            // when
            final Throwable throwable = catchThrowable(() -> createConference.execute(conferenceParams));

            // then
            assertThat(throwable).isInstanceOf(InvalidPricesException.class);
            assertThat(throwable).hasMessage("Price range should be strictly ascending");
        }

        @Test
        void execute_should_throw_exception_when_regular_price_not_strictly_greater_than_last_price_range() {
            // given
            final PriceRangeParams septemberPrice = new PriceRangeParams(150f, LocalDate.of(2022, 9, 1), LocalDate.of(2022, 9, 30));
            final PriceRangeParams octoberPrice = new PriceRangeParams(250f, LocalDate.of(2022, 10, 1), LocalDate.of(2022, 11, 30));
            float defaultPrice = 200f;

            ConferenceParams conferenceParams = new ConferenceParams(
                    "devoxx",
                    "link",
                    LocalDate.of(2022, 12, 1),
                    LocalDate.of(2022, 12, 3),
                    defaultPrice,
                    of(septemberPrice, octoberPrice),
                    null,
                    of()
            );

            // when
            final Throwable throwable = catchThrowable(() -> createConference.execute(conferenceParams));

            // then
            assertThat(throwable).isInstanceOf(InvalidPricesException.class);
            assertThat(throwable).hasMessage("Price range must be lower than default price");
        }

        @Test
        void execute_should_throw_exception_when_any_price_less_than_zero() {
            // given
            final PriceRangeParams septemberPrice = new PriceRangeParams(-50f, LocalDate.of(2022, 9, 1), LocalDate.of(2022, 9, 30));
            final PriceRangeParams octoberPrice = new PriceRangeParams(-20f, LocalDate.of(2022, 10, 1), LocalDate.of(2022, 11, 30));
            float defaultPrice = 10f;

            ConferenceParams conferenceParams = new ConferenceParams(
                  "devoxx",
                  "link",
                  LocalDate.of(2022, 12, 1),
                  LocalDate.of(2022, 12, 3),
                  defaultPrice,
                  List.of(septemberPrice, octoberPrice),
                  null,
                  List.of()
            );

            // when
            final Throwable throwable = catchThrowable(() -> createConference.execute(conferenceParams));

            // then
            assertThat(throwable)
                    .isInstanceOf(InvalidPricesException.class)
                    .hasMessage("Price range greater than zero");
        }

        @Test
        void execute_should_throw_exception_when_same_price_in_price_range() {
            // given
            final PriceRangeParams septemberPrice = new PriceRangeParams(20f, LocalDate.of(2022, 9, 1), LocalDate.of(2022, 9, 30));
            final PriceRangeParams octoberPrice = new PriceRangeParams(20f, LocalDate.of(2022, 10, 1), LocalDate.of(2022, 11, 30));
            float defaultPrice = 30f;

            ConferenceParams conferenceParams = new ConferenceParams(
                    "devoxx",
                    "link",
                    LocalDate.of(2022, 12, 1),
                    LocalDate.of(2022, 12, 3),
                    defaultPrice,
                    List.of(septemberPrice, octoberPrice),
                    null,
                    List.of()
            );

            // when
            final Throwable throwable = catchThrowable(() -> createConference.execute(conferenceParams));

            // then
            assertThat(throwable)
                    .isInstanceOf(InvalidPricesException.class)
                    .hasMessage("Non unique price range");
        }

    }

    @Nested
    class CreateWithPriceGroup {
        @Test
        void execute_should_throw_exception_when_price_group_greater_than_default_price() {
            final PriceGroupParams priceGroupParams = new PriceGroupParams(250f, 3);
            float defaultPrice = 249f;

            ConferenceParams conferenceParams = new ConferenceParams(
                    "devoxx",
                    "link",
                    LocalDate.of(2022, 12, 1),
                    LocalDate.of(2022, 12, 3),
                    defaultPrice,
                    of(),
                    priceGroupParams,
                    of()
            );

            // when
            final Throwable throwable = catchThrowable(() -> createConference.execute(conferenceParams));

            // then
            assertThat(throwable).isInstanceOf(InvalidPricesException.class);
            assertThat(throwable).hasMessage("Price group is greater than default price");
        }

        @Test
        void execute_should_throw_exception_when_threshold_is_less_or_equal_to_1() {
            final PriceGroupParams priceGroupParams = new PriceGroupParams(250f, 1);
            float defaultPrice = 350f;

            ConferenceParams conferenceParams = new ConferenceParams(
                    "devoxx",
                    "link",
                    LocalDate.of(2022, 12, 1),
                    LocalDate.of(2022, 12, 3),
                    defaultPrice,
                    of(),
                    priceGroupParams,
                    of()
            );

            // when
            final Throwable throwable = catchThrowable(() -> createConference.execute(conferenceParams));

            // then
            assertThat(throwable).isInstanceOf(InvalidThresholdException.class);
            assertThat(throwable).hasMessage("Price group threshold must be greater than 1");
        }
    }

    @Nested
    class CreateWithAttendingDays {
        @Test
        void execute_should_throw_exception_when_attending_days_is_more_than_conference_duration() {
            // given
            final List<PriceAttendingDaysParams> priceAttendingDayList = List.of(
                    new PriceAttendingDaysParams(300f, 1000f)
            );
            ConferenceParams conferenceParams = new ConferenceParams(
                    "devoxx",
                    "link",
                    LocalDate.of(2022, 12, 1),
                    LocalDate.of(2022, 12, 3),
                    300f,
                    of(),
                    null,
                    priceAttendingDayList
            );

            // when
            final Throwable throwable = catchThrowable(() -> createConference.execute(conferenceParams));

            // then
            assertThat(throwable).isInstanceOf(InvalidAttendingDaysException.class);
            assertThat(throwable).hasMessage("Attending days must be equal or less than conference duration");
        }

        @Test
        void execute_should_throw_exception_when_we_have_conference_with_duplicated_attending_days() {
            // given
            final List<PriceAttendingDaysParams> priceAttendingDayList = List.of(
                    new PriceAttendingDaysParams(300f, 2f),
                    new PriceAttendingDaysParams(200f, 2f)
            );
            ConferenceParams conferenceParams = new ConferenceParams(
                    "devoxx",
                    "link",
                    LocalDate.of(2022, 12, 1),
                    LocalDate.of(2022, 12, 3),
                    300f,
                    of(),
                    null,
                    priceAttendingDayList
            );

            // when
            final Throwable throwable = catchThrowable(() -> createConference.execute(conferenceParams));

            // then
            assertThat(throwable).isInstanceOf(InvalidAttendingDaysException.class);
            assertThat(throwable).hasMessage("Attending days must be unique for one conference");
        }

        @Test
        void execute_should_throw_exception_when_we_have_conference_with_duplicated_price() {
            // given
            final List<PriceAttendingDaysParams> priceAttendingDayList = List.of(
                    new PriceAttendingDaysParams(300f, 1f),
                    new PriceAttendingDaysParams(300f, 2f)
            );
            ConferenceParams conferenceParams = new ConferenceParams(
                    "devoxx",
                    "link",
                    LocalDate.of(2022, 12, 1),
                    LocalDate.of(2022, 12, 3),
                    300f,
                    of(),
                    null,
                    priceAttendingDayList
            );

            // when
            final Throwable throwable = catchThrowable(() -> createConference.execute(conferenceParams));

            // then
            assertThat(throwable).isInstanceOf(InvalidAttendingDaysException.class);
            assertThat(throwable).hasMessage("Price must be unique for one conference");
        }
    }
}
