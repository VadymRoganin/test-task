package com.company.testtask.controller;

import com.company.testtask.model.dto.CourseRequestDTO;
import com.company.testtask.model.dto.CourseResponseDTO;
import com.company.testtask.model.id.StudentId;
import com.company.testtask.service.CourseService;
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
@RequestMapping("/courses")
@RestController
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public CourseResponseDTO createCourse(@Valid @RequestBody CourseRequestDTO dto) {
        log.info("Executing CourseController.createCourse() method");
        return courseService.createCourse(dto);
    }

    @GetMapping
    public Page<CourseResponseDTO> getCourses(Pageable pageable,
                                              @RequestParam(value = "student_id", required = false) StudentId studentId) {
        log.info("Executing CourseController.getCourses() method {}", isNull(studentId) ? "" : " with studentId = " + studentId);
        return courseService.getCourses(pageable, studentId);
    }

    @GetMapping("{id}")
    public CourseResponseDTO getCourse(@PathVariable("id") Long id) {
        log.info("Executing CourseController.getCourse() method with id = {}", id);
        return courseService.getCourse(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("{id}")
    public void updateCourse(@PathVariable("id") Long id, @Valid @RequestBody CourseRequestDTO dto) {
        log.info("Executing CourseController.updateCourse() method with id = {}", id);
        courseService.updateCourse(id, dto);
    }

    @DeleteMapping("{id}")
    public void deleteCourse(@PathVariable("id") Long id) {
        log.info("Executing CourseController.updateCourse() method with id = {}", id);
        courseService.deleteCourse(id);
    }
}
