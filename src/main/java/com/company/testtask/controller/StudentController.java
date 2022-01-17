package com.company.testtask.controller;

import com.company.testtask.model.id.CourseId;
import com.company.testtask.model.dto.StudentRequestDTO;
import com.company.testtask.model.dto.StudentResponseDTO;
import com.company.testtask.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static java.util.Objects.isNull;

@Slf4j
@Validated
@RequestMapping("students")
@RestController
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public StudentResponseDTO createStudent(@Valid @RequestBody StudentRequestDTO dto) {
        log.info("Executing StudentController.createStudent() method");
        return studentService.createStudent(dto);
    }

    @GetMapping
    public Page<StudentResponseDTO> getStudents(Pageable pageable,
                                               @RequestParam(value = "course_id", required = false) CourseId courseId) {
        log.info("Executing StudentController.getStudents() method {}", isNull(courseId) ? "" : "with course id = " + courseId);
        return studentService.getStudents(pageable, courseId);
    }

    @GetMapping("{id}")
    public StudentResponseDTO getStudent(@PathVariable("id") Long id) {
        log.info("Executing StudentController.getStudent() method with id = {}", id);
        return studentService.getStudent(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("{id}")
    public void updateStudent(@PathVariable("id") Long id, @Valid @RequestBody StudentRequestDTO dto) {
        log.info("Executing StudentController.updateStudent() method with id = {}", id);
        studentService.updateStudent(id, dto);
    }

    @DeleteMapping("{id}")
    public void deleteStudent(@PathVariable("id") Long id) {
        log.info("Executing StudentController.deleteStudent() method with id = {}", id);
        studentService.deleteStudent(id);
    }

}
