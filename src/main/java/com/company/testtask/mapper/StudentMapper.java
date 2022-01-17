package com.company.testtask.mapper;

import com.company.testtask.model.dto.StudentRequestDTO;
import com.company.testtask.model.dto.StudentResponseDTO;
import com.company.testtask.model.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface StudentMapper {

    @Mapping(source = "name", target = "name")
    Student toEntity(StudentRequestDTO dto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "courses", target = "courses")
    StudentResponseDTO toResponseDTO(Student savedEntity);
}
