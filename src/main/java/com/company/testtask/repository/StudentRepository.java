package com.company.testtask.repository;

import com.company.testtask.model.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Page<Student> findAllByCourses_Id(Pageable pageable, Long studentId);
}
