package ru.alexeymalinov.taskautomation.robot;

import ru.alexeymalinov.taskautomation.core.model.Task;
import ru.alexeymalinov.taskautomation.core.services.RobotService;

import java.util.List;

public interface TaskExecutor extends Runnable {

    /**
     * Передает задачу соответсвующему сервису
     * @param task - задача, которую необходимо выполнить
     */
    default void startTask(Task task, List<RobotService> services){
        RobotService service = findService(task, services);
        if(service != null) {
            service.notifyService(task);
        }
    }

    /**
     * Ищет сервис который может выполнить задачу
     * @param task
     * @return
     */
    default RobotService findService(Task task, List<RobotService> services){
        for (RobotService robotService : services) {
            if(robotService.checkTask(task)){
                return robotService;
            }
        }
        return null;
    }

}
