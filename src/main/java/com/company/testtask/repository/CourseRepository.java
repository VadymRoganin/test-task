package com.company.testtask.repository;

import com.company.testtask.model.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Page<Course> findAllByStudents_Id(Pageable pageable, Long id);
}
