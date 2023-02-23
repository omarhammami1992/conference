package com.soat.back.conference.query.infrastructure;

import com.soat.back.conference.query.domain.Conference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;

@SpringBootTest
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "classpath:/delete-conference-entries.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value ="classpath:/insert-conference-entries.sql")})

//@DataJpaTest
class ConferenceAdapterIntegrationTest {

    @Autowired
    ConferenceAdapter conferenceAdapter;


    @Test
    void should_return_all_conferences() {
        List<Conference> conferences = conferenceAdapter.getAll();
        System.out.println("conferences = " + conferences);
    }

    @Test
    void should_get_conference_by_id() {
    }
}