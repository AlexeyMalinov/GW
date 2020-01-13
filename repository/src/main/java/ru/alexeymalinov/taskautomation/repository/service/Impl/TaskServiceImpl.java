package ru.alexeymalinov.taskautomation.repository.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alexeymalinov.taskautomation.repository.db.entity.TaskEntity;
import ru.alexeymalinov.taskautomation.repository.db.repository.TaskRepository;
import ru.alexeymalinov.taskautomation.repository.service.TaskService;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    @Transactional
    public List<TaskEntity> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    @Transactional
    public List<TaskEntity> getTask(String name) {
        return taskRepository.findByName(name);
    }

    @Override
    @Transactional
    public void addTask(TaskEntity taskEntity) {
        taskRepository.save(taskEntity);
    }

    @Override
    @Transactional
    public void deleteTask(Integer id) {
        taskRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteTask(String name) {
        List<TaskEntity> entities = taskRepository.findByName(name);
        for (TaskEntity entity : entities) {
            taskRepository.delete(entity);
        }
    }
}
