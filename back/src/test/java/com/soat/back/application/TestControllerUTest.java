package com.soat.back.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class TestControllerUTest {

    private TestController testController;
    @BeforeEach
    void setUp() {
        testController = new TestController();
    }

    @Test
    void test_should_return_hello_world() {
        // when
        final ResponseEntity<String> result = testController.testEndPoint();

        // then
        assertThat(result.getBody()).isEqualTo("Hello world");
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}