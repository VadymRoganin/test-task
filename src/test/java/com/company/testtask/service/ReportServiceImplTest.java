package com.company.testtask.service;

import com.company.testtask.TestTaskApplication;
import com.company.testtask.config.Config;
import com.company.testtask.mapper.RelationshipMapper;
import com.company.testtask.model.entity.Relationship;
import com.company.testtask.model.id.CourseId;
import com.company.testtask.model.id.StudentId;
import com.company.testtask.repository.CourseStudentRepository;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.mockito.Mockito.when;

@AutoConfigureEmbeddedDatabase
@Sql({"/schema.sql", "/data.sql"})
@SpringBootTest(classes = { TestTaskApplication.class, Config.class })
public class ReportServiceImplTest {

    @MockBean
    private CourseStudentRepository courseStudentRepository;

    @Autowired
    private RelationshipMapper relationshipMapper;

    @Autowired
    private ReportService reportService;

    @Test
    public void shouldGetRelationships() {
        var relationship = new Relationship(1L, 2L);
        when(courseStudentRepository
                .findAll(ArgumentMatchers.<Specification<Relationship>>any(), ArgumentMatchers.any(Pageable.class)))
                    .thenReturn(new PageImpl<>(List.of(relationship)));

        var result = reportService.getRelationships(Pageable.ofSize(10), new StudentId(1L), new CourseId(1L));

        Assertions.assertEquals(1, result.getTotalElements());
        Assertions.assertEquals(relationshipMapper.toRelationshipDTO(relationship), result.iterator().next());
    }
}
