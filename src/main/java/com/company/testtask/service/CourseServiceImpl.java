package com.company.testtask.service;

import com.company.testtask.exception.NotFoundException;
import com.company.testtask.mapper.CourseMapper;
import com.company.testtask.model.dto.CourseRequestDTO;
import com.company.testtask.model.dto.CourseResponseDTO;
import com.company.testtask.model.id.StudentId;
import com.company.testtask.repository.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;

    private final CourseMapper courseMapper;

    public CourseServiceImpl(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    @Override
    public CourseResponseDTO createCourse(CourseRequestDTO dto) {
        var entity = courseMapper.toEntity(dto);
        var savedEntity = courseRepository.save(entity);

        return courseMapper.toResponseDTO(savedEntity);
    }

    @Override
    public CourseResponseDTO getCourse(Long id) {
        var entity = courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Course with id=%d not found", id)));
        return courseMapper.toResponseDTO(entity);
    }

    @Override
    public void updateCourse(Long id, CourseRequestDTO dto) {
        var entity = courseRepository.getById(id);
        entity.setName(dto.name());
        courseRepository.save(entity);
    }

    @Override
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public Page<CourseResponseDTO> getCourses(Pageable pageable, StudentId studentId) {
        if (studentId == null) {
            return courseRepository.findAll(pageable).map(courseMapper::toResponseDTO);
        }

        return courseRepository.findAllByStudents_Id(pageable, studentId.id()).map(courseMapper::toResponseDTO);
    }
}
