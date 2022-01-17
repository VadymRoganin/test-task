package com.company.testtask.model.dto;

import javax.validation.constraints.NotNull;

public record RelationshipDTO(@NotNull Long studentId, @NotNull Long courseId) {
}
