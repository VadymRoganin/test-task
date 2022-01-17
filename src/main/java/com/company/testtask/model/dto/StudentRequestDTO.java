package com.company.testtask.model.dto;

import javax.validation.constraints.NotNull;

public record StudentRequestDTO(@NotNull String name) {
}
