package com.company.testtask.service;

import com.company.testtask.TestTaskApplication;
import com.company.testtask.config.Config;
import com.company.testtask.exception.NotFoundException;
import com.company.testtask.exception.ReachedLimitException;
import com.company.testtask.mapper.StudentMapper;
import com.company.testtask.model.dto.RelationshipDTO;
import com.company.testtask.model.dto.StudentRequestDTO;
import com.company.testtask.model.dto.StudentResponseDTO;
import com.company.testtask.model.entity.Course;
import com.company.testtask.model.entity.Student;
import com.company.testtask.model.id.CourseId;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@AutoConfigureEmbeddedDatabase
@Sql({"/schema.sql", "/data.sql"})
@TestPropertySource(locations="classpath:application-with-limits.properties")
@SpringBootTest(classes = { TestTaskApplication.class, Config.class })
public class StudentServiceImplTest {

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private CourseRepository courseRepository;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private StudentService studentService;

    @Test
    public void shouldCreateStudent() {
        var name = "Student Name";
        var id = 1L;
        var dto = new StudentRequestDTO(name);
        var entity = studentMapper.toEntity(dto);
        var savedEntity = new Student(id, name, null);

        when(studentRepository.save(entity)).thenReturn(savedEntity);

        Assertions.assertEquals(new StudentResponseDTO(id, name, null), studentService.createStudent(dto));
        verify(studentRepository, times(1)).save(entity);
    }

    @Test
    public void shouldGetStudentById() {
        var name = "Student Name";
        var id = 1L;
        var entity = new Student(id, name, null);

        when(studentRepository.findById(id)).thenReturn(Optional.of(entity));

        Assertions.assertEquals(new StudentResponseDTO(id, name, null), studentService.getStudent(id));
        verify(studentRepository, times(1)).findById(id);
    }

    @Test
    public void shouldThrowNotFoundExceptionWhenStudentNotFound() {
        var id = 1L;

        when(studentRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> studentService.getStudent(id));
        verify(studentRepository, times(1)).findById(id);
    }

    @Test
    public void shouldUpdateStudent() {
        var id = 1L;
        var name = "Student Name";
        var dto = new StudentRequestDTO(name);
        var entity = new Student(id, "Old Name", null);
        var updatedEntity = new Student(id, name, null);
        when(studentRepository.getById(id)).thenReturn(entity);

        studentService.updateStudent(id, dto);

        verify(studentRepository, times(1)).getById(id);
        verify(studentRepository, times(1)).save(updatedEntity);
    }

    @Test
    public void shouldGetStudentsByCourseId() {
        var id = 1L;
        var pageable = Pageable.ofSize(10);
        var student = new Student(id, "name", null);
        when(studentRepository.findAllByCourses_Id(pageable,id)).thenReturn(new PageImpl<>(List.of(student)));
        var dto = studentMapper.toResponseDTO(student);

        var result = studentService.getStudents(pageable, new CourseId(id));

        Assertions.assertEquals(1, result.getTotalElements());
        Assertions.assertEquals(dto, result.iterator().next());
        verify(studentRepository, times(1)).findAllByCourses_Id(pageable, id);
    }

    @Test
    public void shouldGetStudents_nullCourseId() {
        var pageable = Pageable.ofSize(10);
        var student = new Student(1L, "name", null);
        when(studentRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(student)));
        var result = studentService.getStudents(pageable, null);
        var dto = studentMapper.toResponseDTO(student);

        Assertions.assertEquals(1, result.getTotalElements());
        Assertions.assertEquals(dto, result.iterator().next());
        verify(studentRepository, times(1)).findAll(pageable);
    }

    @Test
    public void shouldGetStudents_noneCourseId() {
        var pageable = Pageable.ofSize(10);
        var student = new Student(1L, "name", null);
        when(studentRepository.findAllByCourses_Id(pageable, null)).thenReturn(new PageImpl<>(List.of(student)));
        var result = studentService.getStudents(pageable, new CourseId(null));
        var dto = studentMapper.toResponseDTO(student);

        Assertions.assertEquals(1, result.getTotalElements());
        Assertions.assertEquals(dto, result.iterator().next());
        verify(studentRepository, times(1)).findAllByCourses_Id(pageable, null);
    }

    @Test
    public void shouldRegisterToCourse() {
        var studentId = 1L;
        var courseId = 2L;
        var dto = new RelationshipDTO(studentId, courseId);
        var studentEntity = new Student(studentId, "Student name", null);
        var courseEntity = new Course(courseId, "Course name", null);
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(studentEntity));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(courseEntity));
        studentService.registerToCourse(dto);

        verify(studentRepository, times(1)).findById(studentId);
        verify(courseRepository, times(1)).findById(courseId);
        verify(studentRepository, times(1)).save(studentEntity);
    }

    @Test
    public void shouldThrowNotFoundExceptionOnRegisterToCourse_CourseIdNotFound() {
        var studentId = 1L;
        var courseId = 2L;
        var dto = new RelationshipDTO(studentId, courseId);
        var studentEntity = new Student(studentId, "Student name", null);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(studentEntity));
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> studentService.registerToCourse(dto));
    }

    @Test
    public void shouldThrowNotFoundExceptionOnRegisterToCourse_StudentIdNotFound() {
        var studentId = 1L;
        var courseId = 2L;
        var dto = new RelationshipDTO(studentId, courseId);
        var courseEntity = new Course(courseId, "Course name", null);

        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(courseEntity));
        assertThrows(NotFoundException.class, () -> studentService.registerToCourse(dto));
    }

    @Test
    public void shouldThrowLimitExceededExceptionOnCoursesLimit() {
        var studentId = 1L;
        var courseId = 2L;
        var dto = new RelationshipDTO(studentId, courseId);
        var studentEntity = new Student(studentId, "Student name", Set.of(new Course()));
        var courseEntity = new Course(courseId, "Course name", null);
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(studentEntity));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(courseEntity));

        assertThrows(ReachedLimitException.class, () -> studentService.registerToCourse(dto));
    }

    @Test
    public void shouldThrowLimitExceededExceptionOnStudentssLimit() {
        var studentId = 1L;
        var courseId = 2L;
        var dto = new RelationshipDTO(studentId, courseId);
        var studentEntity = new Student(studentId, "Student name", null);
        var courseEntity = new Course(courseId, "Course name", Set.of(new Student()));
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(studentEntity));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(courseEntity));

        assertThrows(ReachedLimitException.class, () -> studentService.registerToCourse(dto));
    }
}
