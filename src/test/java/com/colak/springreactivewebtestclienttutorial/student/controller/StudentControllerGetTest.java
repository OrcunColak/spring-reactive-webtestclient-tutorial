package com.colak.springreactivewebtestclienttutorial.student.controller;

import com.colak.springreactivewebtestclienttutorial.student.dto.StudentDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
class StudentControllerGetTest {
    @LocalServerPort
    private int port;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testGetAllIsEqualTo() {
        List<StudentDto> expected = List.of(
                new StudentDto(1L, "John Doe"),
                new StudentDto(2L, "Jane Doe")
        );
        webTestClient.get()
                .uri("/api/v1/student/list")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(StudentDto.class)
                .hasSize(2)
                .isEqualTo(expected);
    }

    @Test
    void testGetAllContains() {
        StudentDto student1 = new StudentDto(1L, "John Doe");
        StudentDto student2 = new StudentDto(2L, "Jane Doe");

        webTestClient.get()
                .uri("/api/v1/student/list")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(StudentDto.class)
                .hasSize(2)
                .contains(student1, student2);
    }
}
