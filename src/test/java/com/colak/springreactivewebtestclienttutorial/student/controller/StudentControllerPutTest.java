package com.colak.springreactivewebtestclienttutorial.student.controller;

import com.colak.springreactivewebtestclienttutorial.student.dto.StudentDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerPutTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testUpdateStudent() {

        StudentDto updatedStudentDto = new StudentDto(2L, "Updated Name");
        webTestClient.put()
                .uri("/api/v1/student/update/" + updatedStudentDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updatedStudentDto)
                // .body(Mono.just(updatedStudentDto), StudentDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("Student with ID 2 updated successfully");
    }
}
