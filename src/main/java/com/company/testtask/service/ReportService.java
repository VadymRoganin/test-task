package com.company.testtask.service;

import com.company.testtask.model.dto.RelationshipDTO;
import com.company.testtask.model.id.CourseId;
import com.company.testtask.model.id.StudentId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReportService {
    Page<RelationshipDTO> getRelationships(Pageable pageable, StudentId studentId, CourseId courseId);
}
