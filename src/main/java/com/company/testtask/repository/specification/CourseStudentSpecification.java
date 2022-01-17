package com.company.testtask.repository.specification;

import com.company.testtask.model.entity.Relationship;
import com.company.testtask.model.id.CourseId;
import com.company.testtask.model.id.StudentId;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

public class CourseStudentSpecification implements Specification<Relationship> {

    private final StudentId studentId;
    private final CourseId courseId;

    public CourseStudentSpecification(StudentId studentId, CourseId courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    @Override
    public Predicate toPredicate(Root<Relationship> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (nonNull(studentId)) {
            predicates.add(criteriaBuilder.equal(root.get("studentId"), studentId.id()));
        }

        if (nonNull(courseId)) {
            predicates.add(criteriaBuilder.equal(root.get("courseId"), courseId.id()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
