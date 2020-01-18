package ru.alexeymalinov.taskautomation.robot.executor;

import ru.alexeymalinov.taskautomation.core.model.Task;
import ru.alexeymalinov.taskautomation.core.services.RobotService;
import ru.alexeymalinov.taskautomation.core.services.clirobotservice.CliScriptService;
import ru.alexeymalinov.taskautomation.core.services.guirobotservice.GuiScriptService;
import ru.alexeymalinov.taskautomation.robot.TaskExecutor;

import java.util.ArrayList;
import java.util.List;

public class TaskExecutorFactory {

    public static TaskExecutor getTaskExecutor(Task task, List<RobotService> services){
        return new TaskExecutorImpl(task, services);
    }

}
