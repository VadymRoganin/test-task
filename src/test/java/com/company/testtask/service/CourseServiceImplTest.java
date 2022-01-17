package com.company.testtask.service;

import com.company.testtask.TestTaskApplication;
import com.company.testtask.config.Config;
import com.company.testtask.exception.NotFoundException;
import com.company.testtask.mapper.CourseMapper;
import com.company.testtask.model.dto.CourseRequestDTO;
import com.company.testtask.model.dto.CourseResponseDTO;
import com.company.testtask.model.entity.Course;
import com.company.testtask.model.id.CourseId;
import com.company.testtask.model.id.StudentId;
import com.company.testtask.repository.CourseRepository;
import com.company.testtask.repository.StudentRepository;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@AutoConfigureEmbeddedDatabase
@Sql({"/schema.sql", "/data.sql"})
@SpringBootTest(classes = { TestTaskApplication.class, Config.class })
public class CourseServiceImplTest {

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private CourseRepository courseRepository;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseService courseService;

    @Test
    public void shouldCreateCourse() {
        var name = "Course Name";
        var id = 1L;
        var dto = new CourseRequestDTO(name);
        var entity = courseMapper.toEntity(dto);
        var savedEntity = new Course(id, name, null);

        when(courseRepository.save(entity)).thenReturn(savedEntity);

        Assertions.assertEquals(new CourseResponseDTO(id, name), courseService.createCourse(dto));
        verify(courseRepository, times(1)).save(entity);
    }

    @Test
    public void shouldGetStudentById() {
        var name = "Course Name";
        var id = 1L;
        var entity = new Course(id, name, null);

        when(courseRepository.findById(id)).thenReturn(Optional.of(entity));

        Assertions.assertEquals(new CourseResponseDTO(id, name), courseService.getCourse(id));
        verify(courseRepository, times(1)).findById(id);
    }

    @Test
    public void shouldThrowNotFoundExceptionWhenStudentNotFound() {
        var id = 1L;

        when(courseRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> courseService.getCourse(id));
        verify(courseRepository, times(1)).findById(id);
    }

    @Test
    public void shouldUpdateStudent() {
        var id = 1L;
        var name = "Student Name";
        var dto = new CourseRequestDTO(name);
        var entity = new Course(id, "Old Name", null);
        var updatedEntity = new Course(id, name, null);
        when(courseRepository.getById(id)).thenReturn(entity);

        courseService.updateCourse(id, dto);

        verify(courseRepository, times(1)).getById(id);
        verify(courseRepository, times(1)).save(updatedEntity);
    }

    @Test
    public void shouldGetStudentsByCourseId() {
        var id = 1L;
        var pageable = Pageable.ofSize(10);
        var student = new Course(id, "name", null);
        when(courseRepository.findAllByStudents_Id(pageable,id)).thenReturn(new PageImpl<>(List.of(student)));
        var dto = courseMapper.toResponseDTO(student);

        var result = courseService.getCourses(pageable, new StudentId(id));

        Assertions.assertEquals(1, result.getTotalElements());
        Assertions.assertEquals(dto, result.iterator().next());
        verify(courseRepository, times(1)).findAllByStudents_Id(pageable, id);
    }

    @Test
    public void shouldGetStudents_nullCourseId() {
        var pageable = Pageable.ofSize(10);
        var course = new Course(1L, "name", null);
        when(courseRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(course)));
        var result = courseService.getCourses(pageable, null);
        var dto = courseMapper.toResponseDTO(course);

        Assertions.assertEquals(1, result.getTotalElements());
        Assertions.assertEquals(dto, result.iterator().next());
        verify(courseRepository, times(1)).findAll(pageable);
    }

    @Test
    public void shouldGetStudents_noneCourseId() {
        var pageable = Pageable.ofSize(10);
        var course = new Course(1L, "name", null);
        when(courseRepository.findAllByStudents_Id(pageable, null)).thenReturn(new PageImpl<>(List.of(course)));
        var result = courseService.getCourses(pageable, new StudentId(null));
        var dto = courseMapper.toResponseDTO(course);

        Assertions.assertEquals(1, result.getTotalElements());
        Assertions.assertEquals(dto, result.iterator().next());
        verify(courseRepository, times(1)).findAllByStudents_Id(pageable, null);
    }
}
