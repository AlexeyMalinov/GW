package ru.alexeymalinov.taskautomation.core.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import ru.alexeymalinov.taskautomation.core.model.Task;
import ru.alexeymalinov.taskautomation.core.services.guirobotservice.GuiScriptService;

import static org.junit.jupiter.api.Assertions.*;

class RobotServiceTest {

    @Test
    void checkTask() {
        Task taskMock = Mockito.mock(Task.class);
        Mockito.when(taskMock.getServiceName()).thenReturn("ru.alexeymalinov.taskautomation.core.services.guirobotservice.GuiScriptService");
        GuiScriptService guiScriptService = new GuiScriptService();
        assertTrue(guiScriptService.checkTask(taskMock));
    }
}