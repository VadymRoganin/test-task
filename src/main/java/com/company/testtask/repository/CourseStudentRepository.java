package com.company.testtask.repository;

import com.company.testtask.model.entity.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CourseStudentRepository
        extends JpaRepository<Relationship, Relationship.RelationshipId>, JpaSpecificationExecutor<Relationship> {
}
