package com.company.testtask.controller;

import com.company.testtask.model.dto.StudentRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StudentControllerTest extends AbstractControllerTest {

    @Test
    public void shouldCreateStudent() throws Exception {
        var dto = new StudentRequestDTO("New student name");
        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New student name"))
                .andExpect(jsonPath("$.id").value(6));
    }

    @Test
    public void shouldGetStudentsList() throws Exception {
        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(5)));
    }

    @Test
    public void shouldGetStudentsByCourseId() throws Exception {
        registerStudentToCourse(1L, 1L);
        mockMvc.perform(get("/students?course_id=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].name").value("Yuliia"));
    }

    @Test
    public void shouldGetStudentsByCourseId_none() throws Exception {
        registerStudentToCourse(1L, 1L);
        mockMvc.perform(get("/students?course_id=none"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(4)));
    }

    @Test
    public void shouldReturn400ForInvalidCourseId() throws Exception {
        registerStudentToCourse(1L, 1L);
        mockMvc.perform(get("/students?course_id=smth"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("Validation failed"));
    }

    @Test
    public void shouldGetStudentById() throws Exception {
        mockMvc.perform(get("/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Yuliia"));
    }

    @Test
    public void shouldUpdateStudentById() throws Exception {
        var dto = new StudentRequestDTO("Vadim R");
        mockMvc.perform(put("/students/2").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(dto)))
                .andExpect(status().isNoContent());
    }
}
