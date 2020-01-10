package ru.alexeymalinov.taskautomation.core.services;

import ru.alexeymalinov.taskautomation.core.model.Task;

public interface RobotService {

    void notifyService(Task task);

    default boolean checkTask(Task task) {
        return this.getClass().getName().equals(task.getServiceName());
    }
}
