package com.company.testtask.service;

import com.company.testtask.mapper.RelationshipMapper;
import com.company.testtask.model.dto.RelationshipDTO;
import com.company.testtask.model.id.CourseId;
import com.company.testtask.model.id.StudentId;
import com.company.testtask.repository.CourseStudentRepository;
import com.company.testtask.repository.specification.CourseStudentSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReportServiceImpl implements ReportService {

    private final CourseStudentRepository courseStudentRepository;

    private final RelationshipMapper relationshipMapper;

    public ReportServiceImpl(CourseStudentRepository courseStudentRepository, RelationshipMapper relationshipMapper) {
        this.courseStudentRepository = courseStudentRepository;
        this.relationshipMapper = relationshipMapper;
    }

    @Override
    public Page<RelationshipDTO> getRelationships(Pageable pageable, StudentId studentId, CourseId courseId) {
            return courseStudentRepository.findAll(new CourseStudentSpecification(studentId, courseId), pageable)
                    .map(relationshipMapper::toRelationshipDTO);
    }
}
