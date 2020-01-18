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
import java.util.Objects;
import java.util.Properties;

public class TaskExecutorImpl implements TaskExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskExecutorImpl.class);

    private final List<RobotService> services;
    private final Task task;

    public TaskExecutorImpl(Task task, List<RobotService> services){
        this.task = task;
        this.services = services;
    }

     /**
     * Запускает выполнение цепочки задач
     */
    public void run(){
        Task currentTask = task;
        while(currentTask != null){
            startTask(currentTask);
            LOGGER.debug("get Next task : " + task.getNext());
            currentTask = currentTask.getNext();
        }
        LOGGER.debug("TaskExecutor has finished work");
    }

    /**
     * Передает задачу соответсвующему сервису
     * @param task - задача, которую необходимо выполнить
     */
    private void startTask(Task task){
        RobotService service = findService(task);
        if(service != null) {
            LOGGER.info("task: " + task.getName() + " with operation: " + task.getOperationName() + " sent for execution");
            service.notifyService(task);
            LOGGER.info("task: " + task.getName() + " with operation: " + task.getOperationName() + " completed");
        }
    }

    /**
     * Ищет сервис который может выполнить задачу
     * @param task
     * @return
     */
    private RobotService findService(Task task){
        LOGGER.info("search service: " + task.getServiceName());
        for (RobotService robotService : services) {
            if(robotService.checkTask(task)){
                LOGGER.info("service: " + task.getServiceName() + " found");
                return robotService;
            }
        }
        LOGGER.info("service: " + task.getServiceName() + " not found");
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskExecutorImpl that = (TaskExecutorImpl) o;
        return Objects.equals(services, that.services) &&
                Objects.equals(task, that.task);
    }

    @Override
    public int hashCode() {
        return Objects.hash(services, task);
    }

    @Override
    public String toString() {
        return "TaskExecutorImpl{" +
                "services=" + services +
                ", task=" + task +
                '}';
    }
}
