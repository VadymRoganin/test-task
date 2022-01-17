package com.company.testtask.controller;

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReportControllerTest extends AbstractControllerTest {

    private void registerStudents() throws Exception {
        registerStudentToCourse(1L, 1L);
        registerStudentToCourse(2L, 1L);
        registerStudentToCourse(2L, 3L);
    }

    @Test
    public void shouldGetRelationshipInfo() throws Exception {
        registerStudents();

        mockMvc.perform(get("/reports"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(3)))
                .andExpect(jsonPath("$.content[0].studentId").value(1))
                .andExpect(jsonPath("$.content[0].courseId").value(1))
                .andExpect(jsonPath("$.content[1].studentId").value(1))
                .andExpect(jsonPath("$.content[1].courseId").value(2))
                .andExpect(jsonPath("$.content[2].studentId").value(3))
                .andExpect(jsonPath("$.content[2].courseId").value(2));
    }

    @Test
    public void shouldGetRelationshipInfoByStudentId() throws Exception {
        registerStudents();

        mockMvc.perform(get("/reports?student_id=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].studentId").value(1))
                .andExpect(jsonPath("$.content[0].courseId").value(1))
                .andExpect(jsonPath("$.content[1].studentId").value(1))
                .andExpect(jsonPath("$.content[1].courseId").value(2));
    }

    @Test
    public void shouldGetRelationshipInfoByCourseId() throws Exception {
        registerStudents();

        mockMvc.perform(get("/reports?course_id=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].studentId").value(1))
                .andExpect(jsonPath("$.content[0].courseId").value(2))
                .andExpect(jsonPath("$.content[1].studentId").value(3))
                .andExpect(jsonPath("$.content[1].courseId").value(2));
    }

    @Test
    public void shouldGetRelationshipInfoByStudentIdAndCourseId() throws Exception {
        registerStudents();

        mockMvc.perform(get("/reports?student_id=3&course_id=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].studentId").value(3))
                .andExpect(jsonPath("$.content[0].courseId").value(2));
    }

    @Test
    public void shouldReturn400ForInvalidStudentId() throws Exception {
        registerStudents();

        mockMvc.perform(get("/reports?student_id=smth"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value(containsString("Validation failed")));
    }

    @Test
    public void shouldReturn400ForInvalidCourseId() throws Exception {
        registerStudents();

        mockMvc.perform(get("/reports?course_id=smth"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value(containsString("Validation failed")));
    }

    @Test
    public void shouldReturn400ForInvalidCourseIdAndInvalidStudentId() throws Exception {
        registerStudents();

        mockMvc.perform(get("/reports?course_id=sm&course_id=smth"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value(containsString("Validation failed")));
    }
}
