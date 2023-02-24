package com.soat.back.conference.query.infrastructure;

import com.soat.back.common.infrastructure.JpaConference;
import com.soat.back.conference.query.domain.Conference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "classpath:/delete-conference-entries.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "classpath:/insert-conference-entries.sql")})

//@DataJpaTest
class ConferenceAdapterIntegrationTest {

    @Autowired
    ConferenceAdapter conferenceAdapter;


    @Test
    void should_return_all_conferences() {
        List<Conference> conferences = conferenceAdapter.getAll();
        assertThat(conferences).usingRecursiveFieldByFieldElementComparatorIgnoringFields("link").containsExactly(new JpaConference(
                1,
                "conference aaa",
                //"link to aaa",
                LocalDate.of(2023, 5, 15),
                LocalDate.of(2023, 5, 19),
                100f
                ));
    }

    @Test
    void should_return_empty_when_conference_by_id_not_found() {
        var maybeConference = conferenceAdapter.getById(2);

        assertThat(maybeConference).isEmpty();
    }

    @Test
    void should_return_conference_when_conference_by_id_is_found() {
        var maybeConference = conferenceAdapter.getById(1);

        assertThat(maybeConference).contains(new JpaConference(
                1,
                "conference aaa",
                //"link to aaa",
                LocalDate.of(2023, 5, 15),
                LocalDate.of(2023, 5, 19),
                100f
        ));
    }
}