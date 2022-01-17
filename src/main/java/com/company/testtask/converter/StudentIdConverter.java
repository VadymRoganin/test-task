package com.company.testtask.converter;

import com.company.testtask.exception.ConversionFailedException;
import com.company.testtask.model.id.StudentId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Component
public class StudentIdConverter implements Converter<String, StudentId> {

    @Value("${test-task.none.keyword:none}")
    private String noneKeyword;

    @Override
    public StudentId convert(String source) {
        try {
            return new StudentId(source.equals(noneKeyword) ? null : Long.valueOf(source));
        } catch (NumberFormatException e) {
            var message = String.format("Invalid student id: %s", source);
            log.warn(message);
            throw new ConversionFailedException(message);
        }
    }
}
