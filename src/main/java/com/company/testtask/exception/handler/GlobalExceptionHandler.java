package com.company.testtask.exception.handler;

import com.company.testtask.exception.ConversionFailedException;
import com.company.testtask.exception.ReachedLimitException;
import com.company.testtask.exception.apierror.ApiError;
import com.company.testtask.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.time.OffsetDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Value("${test-task.not-found.error-message:Resource not found}")
    public String resourceNotFoundErrorMessage;

    @Value("${test-task.reached-limit.error-message:Reached limit}")
    public String reachedLimitErrorMessage;

    @Value("${test-task.validation-failed.error-message:Validation failed}")
    public String validationFailedErrorMessage;

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ApiError handleEntityNotFoundException() {
        return new ApiError(resourceNotFoundErrorMessage, null, HttpStatus.NOT_FOUND.value(), getTimestamp());
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ApiError handleNotFound() {
        return new ApiError(resourceNotFoundErrorMessage, null, HttpStatus.NOT_FOUND.value(), getTimestamp());
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ReachedLimitException.class)
    public ApiError handleReachedLimit(Exception ex) {
        return new ApiError(reachedLimitErrorMessage, ex.getMessage(), HttpStatus.BAD_REQUEST.value(), getTimestamp());
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ApiError handleConstraintValidation(Exception ex) {
        return new ApiError(validationFailedErrorMessage, ex.getMessage(), HttpStatus.BAD_REQUEST.value(), getTimestamp());
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConversionFailedException.class)
    public ApiError handleConversionFailedException(Exception ex) {
        return new ApiError(validationFailedErrorMessage, null, HttpStatus.BAD_REQUEST.value(), getTimestamp());
    }

    private OffsetDateTime getTimestamp() {
        return OffsetDateTime.now();
    }
}
