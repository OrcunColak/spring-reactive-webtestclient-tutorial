package com.colak.springreactivewebtestclienttutorial.student.service;

import com.colak.springreactivewebtestclienttutorial.student.dto.StudentDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    private final List<StudentDto> students = new ArrayList<>(List.of(
            new StudentDto(1L, "John Doe"),
            new StudentDto(2L, "Jane Doe")

    ));
    private Long nextId = 3L;

    public Mono<String> enrollStudent(StudentDto student) {
        return Mono.fromSupplier(() -> {
            student.setId(nextId++);
            students.add(student);
            return "Student enrolled successfully. Student ID: " + student.getId();
        });
    }

    public Mono<String> updateStudent(Long id, StudentDto updatedStudent) {
        return Mono.fromSupplier(() -> {
            for (StudentDto student : students) {
                if (student.getId().equals(id)) {
                    student.setName(updatedStudent.getName());
                    return "Student with ID " + id + " updated successfully";
                }
            }
            return "Student with ID " + id + " not found";
        });
    }

    public Flux<StudentDto> getAllStudents() {
        return Flux.fromIterable(students);
    }
}
