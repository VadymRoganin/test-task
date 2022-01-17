package com.company.testtask.mapper;

import com.company.testtask.model.entity.Relationship;
import com.company.testtask.model.dto.RelationshipDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface RelationshipMapper {

    @Mapping(source = "studentId", target = "studentId")
    @Mapping(source = "courseId", target = "courseId")
    RelationshipDTO toRelationshipDTO(Relationship relationship);
}
