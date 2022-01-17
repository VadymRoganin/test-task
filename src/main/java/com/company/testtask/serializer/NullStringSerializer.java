package com.company.testtask.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.testcontainers.shaded.org.apache.commons.lang.StringUtils;

import java.io.IOException;

public class NullStringSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String action, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeString(StringUtils.EMPTY);
    }
}
