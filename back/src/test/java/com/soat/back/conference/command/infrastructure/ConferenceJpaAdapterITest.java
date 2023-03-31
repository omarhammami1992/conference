package com.soat.back.conference.command.infrastructure;

import com.soat.back.common.infrastructure.JpaConferenceRepository;
import com.soat.back.conference.command.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ConferenceJpaAdapterITest {

    @Autowired
    ConferenceJpaAdapter conferenceAdapter;

    @Autowired
    JpaConferenceRepository jpaConferenceRepository;

    @Test
    void save_data() throws InvalidAttendingDaysException, InvalidIntervalException, InvalidPricesException {
        Conference conference = Conference.create(
                "conference aaa",
                "link to aaa",
                100f,
                LocalDate.of(2023, 5, 15),
                LocalDate.of(2023, 5, 19),
                "city",
                "country",
                PriceRanges.createEmpty(),
                PriceGroup.createEmpty(),
                PriceAttendingDays.create(List.of(new PriceAttendingDay(100f, 1f)))
        );
        var id = conferenceAdapter.save(conference);
        assertThat(id).isNotNull();
    }
}