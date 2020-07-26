package com.school.enrollment.controllers;

import com.school.enrollment.models.ClassWithStudents;
import com.school.enrollment.models.Student;
import com.school.enrollment.repositories.SchoolClassRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/class")
public class ClassController {
    private final SchoolClassRepository classRepository;

    @GetMapping("{className}/students")
    public ClassWithStudents getStudents(@PathVariable final @NonNull String className) {
        val schoolClass = classRepository
                .findByName(className)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("class %s not found", className)));
        return new ClassWithStudents(
                className,
                schoolClass.getStudents()
                        .stream()
                        .map(s -> new Student(s.getId(), s.getFirstName(), s.getLastName(), s.getNationality(), s.getSchoolClass().getName()))
                        .collect(Collectors.toList())
        );
    }
}