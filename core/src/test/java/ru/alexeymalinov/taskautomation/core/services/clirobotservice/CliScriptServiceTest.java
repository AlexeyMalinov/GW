package ru.alexeymalinov.taskautomation.core.services.clirobotservice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CliScriptServiceTest {

    @Test
    void getOperations() {
        assertArrayEquals(CliOperation.values(), new CliScriptService().getOperations());
    }
}