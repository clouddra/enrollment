package com.school.enrollment.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.enrollment.models.ClassWithStudents;
import com.school.enrollment.models.Student;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ClassControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getClassStudents() throws Exception {
        val className = "2 A";
        val expected = new ClassWithStudents(
                className,
                Arrays.asList(new Student(1L, "John", "Wick", "United States of America", "2 A"),
                        new Student(2L, "Bill", "Gates", "United States of America", "2 A"),
                        new Student(3L, "Jotaro", "Kujo", "Japan", "2 A"),
                        new Student(4L, "Giorno", "Giovanna", "", "2 A"))
        );
        mockMvc.perform(get("/class/{className}/students", "2 A").contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(expected)));
    }

    @Test
    void getNonExistentClass() throws Exception {
        mockMvc.perform(get("/class/{className}/students", "2 C").contentType("application/json"))
                .andExpect(status().isNotFound());
    }
}
