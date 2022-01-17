package com.company.testtask.mapper;

import com.company.testtask.model.dto.CourseRequestDTO;
import com.company.testtask.model.dto.CourseResponseDTO;
import com.company.testtask.model.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CourseMapper {

    @Mapping(source = "name", target = "name")
    Course toEntity(CourseRequestDTO courseRequestDTO);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    CourseResponseDTO toResponseDTO(Course entity);
}
