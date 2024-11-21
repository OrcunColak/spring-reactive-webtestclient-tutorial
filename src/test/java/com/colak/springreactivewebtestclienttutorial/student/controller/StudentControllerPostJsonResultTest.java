package com.colak.springreactivewebtestclienttutorial.student.controller;

import com.colak.springreactivewebtestclienttutorial.student.dto.StudentDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
class StudentControllerPostJsonResultTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testEnrollStudent() {
        StudentDto studentDto = new StudentDto(null, "John Doe");

        // Create a new student and update it
        webTestClient.post()
                .uri("/api/v1/student/enroll")
                .contentType(MediaType.APPLICATION_JSON)
                // We can use bodyValue() or body() to post an object
                .bodyValue(studentDto)
                // .body(Mono.just(StudentDto), StudentDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo(3)
                .jsonPath("$.name").isEqualTo("John Doe");
    }

}
