package com.company.testtask.controller;

import com.company.testtask.model.dto.RelationshipDTO;
import com.company.testtask.model.id.CourseId;
import com.company.testtask.model.id.StudentId;
import com.company.testtask.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.isNull;

@Slf4j
@RequestMapping("/reports")
@Validated
@RestController
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public Page<RelationshipDTO> getRelationships(Pageable pageable, @RequestParam(name = "student_id", required = false) StudentId studentId, @RequestParam(name = "course_id", required = false) CourseId courseId)  {
        log.info("Executing ReportController.getRelationships() with studentId={} and courseId={}",
                isNull(studentId) ? "" : studentId, isNull(courseId) ? "" : courseId);
        return reportService.getRelationships(pageable, studentId, courseId);
    }
}
