package com.company.testtask.exception.apierror;

import com.company.testtask.serializer.NullStringSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.OffsetDateTime;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

public record ApiError(String message, @JsonSerialize(nullsUsing = NullStringSerializer.class) String errorDetails, int status,
                       @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX") OffsetDateTime timestamp) {
}
