package ru.alexeymalinov.taskautomation.core.services;

import ru.alexeymalinov.taskautomation.core.model.Task;

public interface RobotService {

    void execute(Task task);

    Operation[] getOperations();

    default boolean checkTask(Task task) {
        return this.getClass().getName().equals(task.getServiceName());
    }
}
