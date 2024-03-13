package com.colak.springreactivewebtestclienttutorial.student.controller;

import com.colak.springreactivewebtestclienttutorial.student.dto.StudentDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
class StudentControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Order(1)
    void testEnrollStudent() {
        StudentDto studentDto = new StudentDto(null, "John Doe");

        webTestClient.post()
                .uri("/api/v1/student/enroll")
                .contentType(MediaType.APPLICATION_JSON)
                // We can use bodyValue() or body() to post an object
                .bodyValue(studentDto)
                // .body(Mono.just(StudentDto), StudentDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(String.class)
                // We can use value() to access result body
                .value(resultBody -> log.info("Result is : {}", resultBody))
                .isEqualTo("Student enrolled successfully. Student ID: 1");
    }

    @Test
    @Order(2)
    void testUpdateStudent() {
        StudentDto studentDto = new StudentDto(null, "Jane Doe");

        webTestClient.post()
                .uri("/api/v1/student/enroll")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(studentDto)
                // .body(Mono.just(studentDto), StudentDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(String.class)
                .isEqualTo("Student enrolled successfully. Student ID: 2");

        StudentDto updatedStudentDto = new StudentDto(2L, "Updated Name");
        webTestClient.put()
                .uri("/api/v1/student/update/" + updatedStudentDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(studentDto)
                // .body(Mono.just(updatedStudentDto), StudentDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("Student with ID 2 updated successfully");
    }

    @Test
    @Order(3)
    void testGetAllStudents() {
        List<StudentDto> expected = List.of(
                new StudentDto(1L, "John Doe"),
                new StudentDto(2L, "Updated Name")
        );
        webTestClient.get()
                .uri("/api/v1/student/list")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(StudentDto.class)
                .isEqualTo(expected);
    }
}
