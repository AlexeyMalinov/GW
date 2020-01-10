package ru.alexeymalinov.taskautomation.core.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.alexeymalinov.taskautomation.core.model.Task;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.security.PublicKey;

public class Converter<T> {

    public String toJSON (T t) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(t);
    }

    public T toObject(String content, Class<T> cls) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(content, cls);
    }
}
