package com.school.enrollment.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.enrollment.models.Student;
import com.school.enrollment.models.StudentUpdate;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getStudentRecord() throws Exception {
        mockMvc.perform(get("/students/{studentId}", 1).contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(new Student(1L, "John", "Wick", "United States of America", "2 A"))));
    }

    @Test
    void getNonExistentStudentRecord() throws Exception {
        mockMvc.perform(get("/students/{studentId}", 6).contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createStudentRecord() throws Exception {
        val student = new Student(10L, "firstName", "lastName", "nationality", "2 C");
        mockMvc.perform(post("/students").contentType("application/json").content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isCreated());
        mockMvc.perform(get("/students/{studentId}", 10).contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(student)));
    }

    @Test
    void createStudentInvalidData() throws Exception {
        val noFirstName = new Student(10L, null, "lastName", "nationality", "2 C");
        mockMvc.perform(post("/students").contentType("application/json").content(objectMapper.writeValueAsString(noFirstName)))
                .andExpect(status().isBadRequest());

        val noLastName = new Student(10L, "firstName", null, "nationality", "2 C");
        mockMvc.perform(post("/students").contentType("application/json").content(objectMapper.writeValueAsString(noLastName)))
                .andExpect(status().isBadRequest());

        val noClass = new Student(10L, "firstName", "lastName", "nationality", null);
        mockMvc.perform(post("/students").contentType("application/json").content(objectMapper.writeValueAsString(noClass)))
                .andExpect(status().isBadRequest());

        val wrongClassFormat = new Student(10L, "firstName", "lastName", "nationality", "2 2");
        mockMvc.perform(post("/students").contentType("application/json").content(objectMapper.writeValueAsString(wrongClassFormat)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createExistingStudentId() throws Exception {
        val student = new Student(1L, "firstName", "lastName", "nationality", "2 C");
        mockMvc.perform(post("/students").contentType("application/json").content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isConflict());
    }

    @Test
    void updateStudent() throws Exception {
        val updateNationality = new StudentUpdate(1L, null, null, "nationality", null);
        mockMvc.perform(put("/students").contentType("application/json").content(objectMapper.writeValueAsString(updateNationality)))
                .andExpect(status().isOk());

        val updateClass = new StudentUpdate(1L, null, null, null, "2 E");
        mockMvc.perform(put("/students").contentType("application/json").content(objectMapper.writeValueAsString(updateClass)))
                .andExpect(status().isOk());

        val updateFirstName = new StudentUpdate(1L, "firstName", null, null, "2 E");
        mockMvc.perform(put("/students").contentType("application/json").content(objectMapper.writeValueAsString(updateFirstName)))
                .andExpect(status().isOk());

        val updateLastName = new StudentUpdate(1L, null, "lastName", null, "2 E");
        mockMvc.perform(put("/students").contentType("application/json").content(objectMapper.writeValueAsString(updateLastName)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/students/{studentId}", 1).contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(new Student(1L, "firstName", "lastName", "nationality", "2 E"))));
    }

    @Test
    void updateStudentInvalidValues() throws Exception {
        val invalidId = new StudentUpdate(10L, "firstName", "lastName", "nationality", "2 C");
        mockMvc.perform(put("/students").contentType("application/json").content(objectMapper.writeValueAsString(invalidId)))
                .andExpect(status().isNotFound());

        val invalidClass = new StudentUpdate(10L, "firstName", "lastName", "nationality", "2 2");
        mockMvc.perform(put("/students").contentType("application/json").content(objectMapper.writeValueAsString(invalidClass)))
                .andExpect(status().isBadRequest());

        val emptyFirstName = new StudentUpdate(10L, "", "lastName", "nationality", "2 2");
        mockMvc.perform(put("/students").contentType("application/json").content(objectMapper.writeValueAsString(emptyFirstName)))
                .andExpect(status().isBadRequest());

        val emptyLastName = new StudentUpdate(10L, "firstName", "", "nationality", "2 2");
        mockMvc.perform(put("/students").contentType("application/json").content(objectMapper.writeValueAsString(emptyLastName)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteStudentRecord() throws Exception {
        mockMvc.perform(delete("/students/{studentId}", 1))
                .andExpect(status().isOk());
    }

    @Test
    void deleteExistentStudentRecord() throws Exception {
        mockMvc.perform(delete("/students/{studentId}", 10))
                .andExpect(status().isNotFound());
    }
}
