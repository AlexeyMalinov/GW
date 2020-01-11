package ru.alexeymalinov.taskautomation.repository;

import ru.alexeymalinov.taskautomation.core.model.Task;

public interface Repository {

    Task getTask(String taskName);

}
