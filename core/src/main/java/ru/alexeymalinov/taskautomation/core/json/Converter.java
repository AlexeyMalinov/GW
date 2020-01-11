package ru.alexeymalinov.taskautomation.core.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class Converter<T> {

    public String toJSON(T t) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(t);
    }

    public void toJSON(File file, T t) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(file, t);
    }

    public T toObject(String content, Class<T> cls) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(content, cls);
    }

    public T toObject(File file, Class<T> cls) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(file, cls);
    }
}
