package com.company.testtask.controller;

import com.company.testtask.model.dto.CourseRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CourseControllerTest extends AbstractControllerTest {

    @Test
    public void shouldCreateCourse() throws Exception {
        var dto = new CourseRequestDTO("New course name");
        mockMvc.perform(post("/courses")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New course name"))
                .andExpect(jsonPath("$.id").value(6));

    }

    @Test
    public void shouldGetCoursesList() throws Exception {
        mockMvc.perform(get("/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(5)));
    }

    @Test
    public void shouldGetCourseById() throws Exception {
        mockMvc.perform(get("/courses/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Python"));
    }

    @Test
    public void shouldGetCoursesByStudentId() throws Exception {
        registerStudentToCourse(1L, 1L);
        mockMvc.perform(get("/courses?student_id=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].name").value("Java"));
    }

    @Test
    public void shouldGetCoursesByStudentId_none() throws Exception {
        registerStudentToCourse(1L, 1L);
        mockMvc.perform(get("/courses?student_id=none"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(4)));
    }

    @Test
    public void shouldReturn400ForInvalidStudentId() throws Exception {
        registerStudentToCourse(1L, 1L);
        mockMvc.perform(get("/courses?student_id=smth"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value(containsString("Validation failed")));
    }

    @Test
    public void shouldUpdateCourseById() throws Exception {
        var dto = new CourseRequestDTO("Python");
        mockMvc.perform(put("/courses/2").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(dto)))
                .andExpect(status().isNoContent());
    }
}
