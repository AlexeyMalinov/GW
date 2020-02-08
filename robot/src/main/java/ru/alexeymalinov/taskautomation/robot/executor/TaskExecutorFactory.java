package ru.alexeymalinov.taskautomation.robot.executor;

import ru.alexeymalinov.taskautomation.core.model.Job;
import ru.alexeymalinov.taskautomation.core.model.Task;
import ru.alexeymalinov.taskautomation.core.services.RobotService;
import ru.alexeymalinov.taskautomation.robot.TaskExecutor;
import ru.alexeymalinov.taskautomation.robot.connection.OrchestratorConnection;

import java.util.List;

public class TaskExecutorFactory {

    public static TaskExecutor getTaskExecutor(Task task, List<RobotService> services) {
        return new LocalTaskExecutor(task, services);
    }

    public static TaskExecutor getTaskExecutor(Job job, Task task, List<RobotService> service, OrchestratorConnection connection) {
        return new OrchestratorTaskExecutor(connection,job, service, task);
    }

}
