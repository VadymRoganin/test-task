package com.company.testtask.controller;

import com.company.testtask.model.dto.RelationshipDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource(locations="classpath:application-with-limits.properties")
public class RelationshipControllerTest extends AbstractControllerTest {

    @Test
    public void shouldRegisterStudentToCourse() throws Exception {
        registerStudentToCourse(1L, 1L);

        mockMvc.perform(get("/reports"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].studentId").value(1))
                .andExpect(jsonPath("$.content[0].courseId").value(1));
    }

    @Test
    public void shouldReturn400OnStudentsLimit() throws Exception {
        registerStudentToCourse(1L, 1L);
        var dto = new RelationshipDTO(1L, 2L);

        mockMvc.perform(post("/relationships")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturn400OnCoursesLimit() throws Exception {
        registerStudentToCourse(1L, 1L);
        var dto = new RelationshipDTO(1L, 2L);

        mockMvc.perform(post("/relationships")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

}
