package ru.alexeymalinov.taskautomation.core.services;

import ru.alexeymalinov.taskautomation.core.model.Task;

import java.util.List;

public interface RobotService {

    void notifyService(Task task);
    Operation[] getOperations();

    default boolean checkTask(Task task) {
        return this.getClass().getName().equals(task.getServiceName());
    }
}
