package com.soat.back.application;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TestController.class)
class TestControllerITest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void testEndPoint_should_return_json() throws Exception {
        // when
        final ResultActions resultActions = mockMvc.perform(get("/test"));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().string("Hello world"));
    }
}