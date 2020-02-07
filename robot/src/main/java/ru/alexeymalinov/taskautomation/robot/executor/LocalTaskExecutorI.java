package ru.alexeymalinov.taskautomation.robot.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.alexeymalinov.taskautomation.core.model.Task;
import ru.alexeymalinov.taskautomation.core.services.RobotService;
import ru.alexeymalinov.taskautomation.robot.TaskExecutor;

import java.util.List;
import java.util.Objects;

public class LocalTaskExecutorI implements TaskExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocalTaskExecutorI.class);

    private final List<RobotService> services;
    private final Task task;

    public LocalTaskExecutorI(Task task, List<RobotService> services){
        this.task = task;
        this.services = services;
    }

     /**
     * Запускает выполнение цепочки задач
     */
    public void run(){
        Task currentTask = task;
        while(currentTask != null){
            startTask(currentTask, services);
            LOGGER.debug("get Next task : " + task.getNext());
            currentTask = currentTask.getNext();
        }
        LOGGER.debug("TaskExecutor has finished work");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocalTaskExecutorI that = (LocalTaskExecutorI) o;
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
