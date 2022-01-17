package com.company.testtask.service;

import com.company.testtask.model.id.CourseId;
import com.company.testtask.model.dto.RelationshipDTO;
import com.company.testtask.model.dto.StudentRequestDTO;
import com.company.testtask.model.dto.StudentResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentService {
    StudentResponseDTO createStudent(StudentRequestDTO dto);

    StudentResponseDTO getStudent(Long id);

    void updateStudent(Long id, StudentRequestDTO dto);

    void deleteStudent(Long id);

    Page<StudentResponseDTO> getStudents(Pageable pageable, CourseId courseId);

    void registerToCourse(RelationshipDTO relationshipDTO);
}
