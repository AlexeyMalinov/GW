package ru.alexeymalinov.taskautomation.repository.service;

import org.springframework.stereotype.Service;
import ru.alexeymalinov.taskautomation.core.model.Task;
import ru.alexeymalinov.taskautomation.repository.db.entity.TaskEntity;

import java.util.List;

@Service
public interface TaskService {
    List<TaskEntity> getAllTasks();
    List<TaskEntity> getTask(String name);
    void addTask(Task task);
    void deleteTask(Integer id);
    void deleteTask(String name);
}
