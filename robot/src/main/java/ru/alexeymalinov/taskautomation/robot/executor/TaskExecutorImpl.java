package ru.alexeymalinov.taskautomation.robot.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.alexeymalinov.taskautomation.core.model.Task;
import ru.alexeymalinov.taskautomation.core.services.RobotService;
import ru.alexeymalinov.taskautomation.core.services.clirobotservice.CliScriptService;
import ru.alexeymalinov.taskautomation.core.services.guirobotservice.GuiScriptService;
import ru.alexeymalinov.taskautomation.robot.TaskExecutor;

import java.util.ArrayList;
import java.util.List;

public class TaskExecutorImpl implements TaskExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskExecutorImpl.class);

    private List<RobotService> services;
    private Task task;

    public TaskExecutorImpl(Task task){
        this.task = task;
        services = initializeServices();
    }

    /**
     * Инициализирует список существующих в роботе сервисов
     * TODO нужно убрать инициализацию из кода, использовать Spring DI
     */
    private static List<RobotService> initializeServices() {
        List<RobotService> services = new ArrayList<>();
        services.add(new CliScriptService());
        services.add(new GuiScriptService());
        return services;
    }
    /**
     * Запускает выполнение цепочки задач
     */
    public void run(){
        while(task != null){
            startTask(task);
            task = task.getNext();
        }
    }

    /**
     * Передает задачу соответсвующему сервису
     * @param task - задача, которую необходимо выполнить
     */
    private void startTask(Task task){
        RobotService service = findService(task);
        if(service != null) {
            service.notifyService(task);
        }
    }

    /**
     * Ищет сервис который может выполнить задачу
     * @param task
     * @return
     */
    private RobotService findService(Task task){
        for (RobotService robotService : services) {
            if(robotService.checkTask(task)){
                return robotService;
            }
        }
        return null;
    }


}
