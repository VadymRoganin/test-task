package com.company.testtask.converter;

import com.company.testtask.exception.ConversionFailedException;
import com.company.testtask.model.id.CourseId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CourseIdConverter implements Converter<String, CourseId> {

    @Value("${test-task.none.keyword:none}")
    private String noneKeyword;

    @Override
    public CourseId convert(String source) {
        try {
            return new CourseId(source.equals(noneKeyword) ? null : Long.valueOf(source));
        } catch (NumberFormatException e) {
            var message = String.format("Invalid course id: %s", source);
            log.warn(message);
            throw new ConversionFailedException(message);
        }
    }
}
