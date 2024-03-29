package com.soat.back.conference.command.infrastructure;

import com.soat.back.common.infrastructure.JpaConferenceRepository;
import com.soat.back.conference.command.domain.Conference;
import com.soat.back.conference.command.domain.InvalidAttendingDaysException;
import com.soat.back.conference.command.domain.PriceAttendingDay;
import com.soat.back.conference.command.domain.PriceAttendingDays;
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
    void save_data() throws InvalidAttendingDaysException {
        Conference conference = Conference.builder()
                .name("conference aaa")
                .link("link to aaa")
                .price(100f)
                .startDate(LocalDate.of(2023, 5, 15))
                .endDate(LocalDate.of(2023, 5, 19))
                .city("city")
                .country("country")
                .priceAttendingDays(PriceAttendingDays.create(List.of(new PriceAttendingDay(100f, 1f))))
                .build();
        var id = conferenceAdapter.save(conference);
        assertThat(id).isNotNull();
    }
}