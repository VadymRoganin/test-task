package com.company.testtask.service;

import com.company.testtask.model.dto.CourseRequestDTO;
import com.company.testtask.model.dto.CourseResponseDTO;
import com.company.testtask.model.id.StudentId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseService {
    CourseResponseDTO createCourse(CourseRequestDTO dto);

    CourseResponseDTO getCourse(Long id);

    void updateCourse(Long id, CourseRequestDTO dto);

    void deleteCourse(Long id);

    Page<CourseResponseDTO> getCourses(Pageable pageable, StudentId studentId);
}
