package com.school.enrollment.models;

import lombok.Value;

import java.util.List;

@Value
public class ClassWithStudents {
    String className;
    List<Student> students;
}
