package ru.alexeymalinov.taskautomation.core.services.guirobotservice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GuiScriptServiceTest {

    @Test
    void getOperations() {
        assertArrayEquals(GuiOperation.values(), new GuiScriptService().getOperations());
    }
}