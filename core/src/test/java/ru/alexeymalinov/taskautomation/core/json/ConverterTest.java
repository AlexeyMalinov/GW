package ru.alexeymalinov.taskautomation.core.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import ru.alexeymalinov.taskautomation.core.model.Task;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class ConverterTest {

    private String expectedJson = "{\"next\":null,\"name\":\"open_notepad\",\"serverLabel\":null,\"serviceName\":\"ru.alexeymalinov.taskautomation.core.services.clirobotservice.CliScriptService\",\"value\":\"notepad\",\"operationName\":\"EXECUTE\"}";
    private Task expectedTask = new Task(null, "open_notepad", null, "ru.alexeymalinov.taskautomation.core.services.clirobotservice.CliScriptService", "EXECUTE", "notepad");
    private Converter<Task> converter = new Converter<>();
    private String actualJsonFile = "./src/test/resources/actualTask.json";
    private String expectedJsonFile = "./src/test/resources/expectedTask.json";

    @Test
    void toJSON() {
        String json = null;
        try {
            json = converter.toJSON(expectedTask);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        assertEquals(expectedJson, json);
    }

    @Test
    void toObject() {
        Task out = null;
        try {
            out = converter.toObject(expectedJson, Task.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        assertEquals(expectedTask, out);
    }

    @Test
    void jsonFileToObject(){
        File file = new File(expectedJsonFile);
        assertTrue(file.exists());
        Task actualTask = null;
        try {
            actualTask = converter.toObject(file, Task.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(expectedTask, actualTask);
    }
    @Test
    void toJSONFile(){
        File file = new File(actualJsonFile);
        try {
            converter.toJSON(file, expectedTask);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            assertEquals(expectedJson, read(actualJsonFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertTrue(file.delete());

    }

    private String read(String path) throws IOException {
        return Files.readString(Paths.get(path));
    }
}