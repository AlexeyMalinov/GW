package ru.alexeymalinov.taskautomation.core.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class Converter<T> {

    /**
     * Преобразует объект в JSON и записывает его в строку
     * @param t
     * @return
     * @throws JsonProcessingException
     */
    public String toJSON(T t) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(t);
    }

    /**
     * Преборазует объект в JSON и записывает его в файл
     * @param file
     * @param t
     * @throws IOException
     */
    public void toJSON(File file, T t) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(file, t);
    }

    /**
     * Преобразует JSON, переданный в виде строки, в объект
     * @param content
     * @param cls
     * @return
     * @throws JsonProcessingException
     */
    public T toObject(String content, Class<T> cls) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(content, cls);
    }

    /**
     * Преобразует JSON, переданный в виде файла, в объект
     * @param file
     * @param cls
     * @return
     * @throws IOException
     */
    public T toObject(File file, Class<T> cls) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(file, cls);
    }
}
