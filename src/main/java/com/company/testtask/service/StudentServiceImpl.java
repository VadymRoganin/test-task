package com.company.testtask.service;

import com.company.testtask.model.id.CourseId;
import com.company.testtask.exception.NotFoundException;
import com.company.testtask.exception.ReachedLimitException;
import com.company.testtask.mapper.StudentMapper;
import com.company.testtask.model.dto.RelationshipDTO;
import com.company.testtask.model.dto.StudentRequestDTO;
import com.company.testtask.model.dto.StudentResponseDTO;
import com.company.testtask.model.entity.Course;
import com.company.testtask.model.entity.Student;
import com.company.testtask.repository.CourseRepository;
import com.company.testtask.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;

import static java.util.Objects.nonNull;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    private final CourseRepository courseRepository;

    private final StudentMapper studentMapper;

    @Value("${test-task.courses.max.amount}")
    private Integer coursesMaxAmount;

    @Value("${test-task.students.max.amount}")
    private Integer studentsMaxAmount;

    @Value("${test-task.not-found.error-message:Resource not found}")
    public String resourceNotFoundErrorMessage;

    @Value("${test-task.reached-limit.error-message:Reached limit}")
    public String reachedLimitErrorMessage;

    public StudentServiceImpl(StudentRepository studentRepository, CourseRepository courseRepository,
                              StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.studentMapper = studentMapper;
    }

    @Override
    public StudentResponseDTO createStudent(StudentRequestDTO dto) {
        var entity = studentMapper.toEntity(dto);
        var savedEntity = studentRepository.save(entity);

        return studentMapper.toResponseDTO(savedEntity);
    }

    @Override
    public StudentResponseDTO getStudent(Long id) {
        Student entity = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Student with id=%d not found", id)));
        return studentMapper.toResponseDTO(entity);
    }

    @Override
    public void updateStudent(Long id, StudentRequestDTO dto) {
        Student entity = studentRepository.getById(id);
        entity.setName(dto.name());
        studentRepository.save(entity);
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public Page<StudentResponseDTO> getStudents(Pageable pageable, CourseId courseId) {
        if (courseId == null) {
            return studentRepository.findAll(pageable).map(studentMapper::toResponseDTO);
        }

        return studentRepository.findAllByCourses_Id(pageable, courseId.id()).map(studentMapper::toResponseDTO);
    }

    @Override
    public void registerToCourse(RelationshipDTO relationshipDTO) {
        var studentId = relationshipDTO.studentId();
        var student = studentRepository.findById(studentId)
                .orElseThrow(() -> {
                    var message = String.format("Student with such id=%d not found", studentId);
                    log.warn(message);
                    return new NotFoundException(message);
                });
        var courseId = relationshipDTO.courseId();
        var course = courseRepository.findById(courseId)
                .orElseThrow(() -> {
                    var message = String.format("Course with such id=%d not found", courseId);
                    log.warn(message);
                    return new NotFoundException(message);
                });

        var studentCourses = nonNull(student.getCourses()) ? student.getCourses() : new HashSet<Course>();
        if (studentCourses.size() >= coursesMaxAmount && !studentCourses.contains(course)) {
            var message = "Courses limit per student reached!";
            log.warn(message);
            throw new ReachedLimitException(message);
        }
        var courseStudents = nonNull(course.getStudents()) ? course.getStudents() : new HashSet<Student>();
        if (courseStudents.size() >= studentsMaxAmount && !studentCourses.contains(course)) {
            var message = "Students limit per course reached!";
            log.warn(message);
            throw new ReachedLimitException(message);
        }
        courseStudents.add(student);
        studentCourses.add(course);
        student.setCourses(studentCourses);
        studentRepository.save(student);
    }
}
