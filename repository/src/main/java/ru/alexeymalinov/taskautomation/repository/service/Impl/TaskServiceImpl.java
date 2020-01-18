package ru.alexeymalinov.taskautomation.repository.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alexeymalinov.taskautomation.core.json.Converter;
import ru.alexeymalinov.taskautomation.core.model.Task;
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
    public TaskEntity getTask(String name) {
        return taskRepository.findByName(name);
    }

    @Override
    @Transactional
    public void addTask(Task task) {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setName(task.getName());
        Converter<Task> converter = new Converter<>();
        try {
            taskEntity.setValue(converter.toJSON(task));
        } catch (JsonProcessingException e) {
            //TODO Логирование
            e.printStackTrace();
        }
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
        TaskEntity entity = taskRepository.findByName(name);
        taskRepository.delete(entity);
    }
}
