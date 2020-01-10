package ru.alexeymalinov.taskautomation.core.services;

import ru.alexeymalinov.taskautomation.core.model.Task;

public interface RobotService {
    void notifyService(Task task);
    boolean checkTask(Task task);
}
