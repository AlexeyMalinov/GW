package ru.alexeymalinov.taskautomation.robot.executor;

import ru.alexeymalinov.taskautomation.core.model.Task;
import ru.alexeymalinov.taskautomation.robot.TaskExecutor;

import java.util.Properties;

public class TaskExecutorFactory {

    public static TaskExecutor getTaskExecutor(Task task, Properties properties){
        return new TaskExecutorImpl(task, properties);
    }
}
