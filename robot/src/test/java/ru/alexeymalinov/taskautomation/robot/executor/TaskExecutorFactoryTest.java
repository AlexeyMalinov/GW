package ru.alexeymalinov.taskautomation.robot.executor;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.alexeymalinov.taskautomation.core.model.Task;
import ru.alexeymalinov.taskautomation.core.services.RobotService;
import ru.alexeymalinov.taskautomation.robot.TaskExecutor;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class TaskExecutorFactoryTest {

    @Test
    void getTaskExecutor() {
        Task task = new Task();
        RobotService serviceMock = Mockito.mock(RobotService.class);
        TaskExecutor actualExecutor = TaskExecutorFactory.getTaskExecutor(task, Collections.singletonList(serviceMock));
        TaskExecutor expectedExecutor = new LocalTaskExecutor(task, Collections.singletonList(serviceMock));
        assertEquals(expectedExecutor, actualExecutor);
    }
}