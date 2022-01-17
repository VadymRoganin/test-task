package com.company.testtask.config;

import com.company.testtask.mapper.CourseMapper;
import com.company.testtask.mapper.RelationshipMapper;
import com.company.testtask.mapper.StudentMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public CourseMapper courseMapper() {
        return Mappers.getMapper(CourseMapper.class);
    }

    @Bean
    public StudentMapper studentMapper() {
        return Mappers.getMapper(StudentMapper.class);
    }

    @Bean
    public RelationshipMapper relationshipDTOMapper() {
        return Mappers.getMapper(RelationshipMapper.class);
    }

}
