package com.company.testtask.model.dto;

import java.util.Set;

public record StudentResponseDTO(Long id, String name, Set<CourseResponseDTO> courses) {
}
