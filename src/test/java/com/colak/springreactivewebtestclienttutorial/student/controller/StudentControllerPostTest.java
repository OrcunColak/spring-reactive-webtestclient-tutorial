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
class StudentControllerPostTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testEnrollStudent() {
        StudentDto studentDto = new StudentDto(null, "John Doe");
        StudentDto expedtedStudentDto = new StudentDto(3L, "John Doe");

        // Create a new student and update it
        webTestClient.post()
                .uri("/api/v1/student/enroll")
                .contentType(MediaType.APPLICATION_JSON)
                // We can use bodyValue() or body() to post an object
                .bodyValue(studentDto)
                // .body(Mono.just(StudentDto), StudentDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(StudentDto.class)
                // We can use value() to access result body
                .value(resultBody -> log.info("Result is : {}", resultBody))
                .isEqualTo(expedtedStudentDto);
    }

}
