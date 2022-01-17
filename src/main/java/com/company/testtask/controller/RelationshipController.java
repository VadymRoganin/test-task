package com.company.testtask.controller;

import com.company.testtask.model.dto.RelationshipDTO;
import com.company.testtask.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@Validated
@RequestMapping("/relationships")
@RestController
public class RelationshipController {

    private final StudentService studentService;

    public RelationshipController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public void registerStudentToTheCourse(@Valid @RequestBody RelationshipDTO relationshipDTO) {
        log.info("Executing RelationshipController.registerStudentToTheCourse()");
        studentService.registerToCourse(relationshipDTO);
    }
}
