package com.soat.back.conference.command.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.soat.back.common.domain.cqrs.CommandResponse;
import com.soat.back.common.domain.ConferenceId;
import com.soat.back.conference.command.SaveConferenceCommand;
import com.soat.back.conference.event.SaveConferenceSucceeded;
import com.soat.back.common.infrastructure.middleware.command.CommandBus;
import com.soat.back.common.infrastructure.middleware.command.CommandBusFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ConferenceController.class)
@ActiveProfiles("test")
class ConferenceControllerITest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommandBus commandBus;

    @MockBean
    private CommandBusFactory commandBusFactory;

    @BeforeEach
    void setUp() {
        when(commandBusFactory.build()).thenReturn(commandBus);
    }

    @Nested
    class Save {
        @Test
        void should_post_ConferenceToSaveJson_and_return_id() throws Exception {
            // given
            ConferenceToSaveJson conferenceToSaveJson = new ConferenceToSaveJson("DEVOXX", "https:www.devoxx", "01-01-2022", "03-01-2022");
            final String requestJson = toJson(conferenceToSaveJson);

            final SaveConferenceSucceeded saveConferenceSucceeded = new SaveConferenceSucceeded(new ConferenceId(1));
            when(commandBus.dispatch(any(SaveConferenceCommand.class))).thenReturn(new CommandResponse<>(saveConferenceSucceeded));

            // when
            final ResultActions resultActions = mockMvc.perform(
                    post("/conference")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestJson)
            );

            // then
            resultActions.andExpect(status().isCreated())
                    .andExpect(content().string("1"));
        }
    }

    private String toJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        return objectWriter.writeValueAsString(object);
    }
}