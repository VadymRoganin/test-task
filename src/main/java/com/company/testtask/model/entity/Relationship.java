package com.company.testtask.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "course_student")
@Entity
@IdClass(Relationship.RelationshipId.class)
public class Relationship implements Serializable {

    @Id
    @Column(name = "student_id")
    private Long studentId;

    @Id
    @Column(name = "course_id")
    private Long courseId;

    @Data
    public static class RelationshipId implements Serializable {
        private Long studentId;

        private Long courseId;
    }
}


