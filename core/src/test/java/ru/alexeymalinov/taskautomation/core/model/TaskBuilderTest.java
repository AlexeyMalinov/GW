package ru.alexeymalinov.taskautomation.core.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskBuilderTest {

    Task expectedTask = new Task(null, "open_notepad", null, "ru.alexeymalinov.taskautomation.core.services.clirobotservice.CliScriptService", "EXECUTE", "notepad");

    @Test
    void create() {
        Task actual = Task.builder("open_notepad")
                .operationName("EXECUTE")
                .serviceName("ru.alexeymalinov.taskautomation.core.services.clirobotservice.CliScriptService")
                .value("notepad")
                .create();
        assertEquals(expectedTask, actual);
    }
}