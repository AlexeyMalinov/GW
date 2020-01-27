package ru.alexeymalinov.taskautomation.repository.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.alexeymalinov.taskautomation.core.model.Task;
import ru.alexeymalinov.taskautomation.repository.db.entity.TaskEntity;
import ru.alexeymalinov.taskautomation.repository.service.TaskService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MainControllerTest {

    @Test
    void allTasks() {
        TaskService taskServiceMock = Mockito.mock(TaskService.class);
        MainController main = new MainController(taskServiceMock);
        Mockito.when(taskServiceMock.getAllTasks()).thenReturn(createTaskEntityList());
        List<TaskEntity> entityList = main.allTasks();
        for (int i = 1; i < 11; i++) {
            assertEquals(createTaskEntity(i), entityList.get(i - 1));
        }
    }

    @Test
    void getTaskByName() {
        TaskService taskServiceMock = Mockito.mock(TaskService.class);
        MainController main = new MainController(taskServiceMock);
        Mockito.when(taskServiceMock.getTask("test")).thenReturn(createTaskEntity(1));
        assertEquals("test", main.getTaskByName("test"));
    }

    @Test
    void addTask() {
        TaskService taskServiceMock = Mockito.mock(TaskService.class);
        MainController main = new MainController(taskServiceMock);
        String out = main.addTask(createTask());
        assertEquals("{\"result\":\"success\", \"error\": \"\"}", out);
    }

    @Test
    void deleteTask() {
        TaskService taskServiceMock = Mockito.mock(TaskService.class);
        MainController main = new MainController(taskServiceMock);
        String out = main.deleteTask("test");
        assertEquals("{\"result\":\"success\", \"error\": \"\"}", out);
    }

    private Task createTask(){
        Task task = new Task();
        task.setName("test");
        task.setValue("test");
        return task;
    }

    private TaskEntity createTaskEntity(int id){
        return new TaskEntity(id,"test","test");
    }

    private List<TaskEntity> createTaskEntityList(){
        List<TaskEntity> list = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            list.add(createTaskEntity(i));
        }
        return list;
    }
}