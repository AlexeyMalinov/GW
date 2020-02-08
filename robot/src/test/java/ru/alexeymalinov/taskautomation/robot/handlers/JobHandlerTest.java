package ru.alexeymalinov.taskautomation.robot.handlers;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import ru.alexeymalinov.taskautomation.core.model.Job;
import ru.alexeymalinov.taskautomation.core.model.Task;
import ru.alexeymalinov.taskautomation.core.repository.Repository;
import ru.alexeymalinov.taskautomation.core.services.RobotService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class JobHandlerTest {

    @Test
    void schedule() {
        Job job = new Job("minimize_all_windows","minimize_all_windows", "http://localhost:8080", LocalDateTime.now(),1,0, TimeUnit.SECONDS);
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        RobotService serviceMock = Mockito.mock(RobotService.class);
        Repository repositoryMock = Mockito.mock(Repository.class);
        Task taskMock = Mockito.mock(Task.class);
        Mockito.when(taskMock.getServiceName()).thenReturn("ru.alexeymalinov.taskautomation.core.services.RobotService");
        Mockito.when(repositoryMock.getTask("minimize_all_windows")).thenReturn(taskMock);
        Mockito.when(serviceMock.checkTask(taskMock)).thenReturn(true);
        Handler handler= new JobFileHandler(Collections.singletonList(serviceMock), pool, "./src/test/resources/jobs.txt");
        handler.schedule(job);
    }
}