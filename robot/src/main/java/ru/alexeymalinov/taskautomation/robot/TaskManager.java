package ru.alexeymalinov.taskautomation.robot;

import ru.alexeymalinov.taskautomation.core.model.Task;
import ru.alexeymalinov.taskautomation.core.services.RobotService;

import java.util.ArrayList;
import java.util.List;

public class TaskManager {

    private List<RobotService> services;

    public TaskManager(){
        services = new ArrayList<>();
    }

    public TaskManager(List<RobotService> services){
        this.services = services;
    }

    /**
     * Добавляет сервис в список сервисов
     * @param service
     */
    public void addService(RobotService service){
        services.add(service);
    }

    /**
     * Удаляет сервис из списка
     * @param service
     */
    public void removeService(RobotService service){
        services.remove(service);
    }

    /**
     * Добавляет цепочку задач в диспетчер, для ее выполнения
     * @param task
     */
    public void addTask(Task task){
        while(task != null){
            startTask(task);
            task = task.getNextTask();
        }
    }

    /**
     * Запускает задачу
     * @param task - задача, которую необходимо выполнить
     * @return возвращает {@code true}, если задачу удалось отправить на выполнение, в противном случаи {@code false}
     */
    private boolean startTask(Task task){
        RobotService service = findService(task);
        if(service != null) {
            service.notifyService(task);
            return true;
        }
        return false;
    }

    /**
     * Ищит сервис который может выполнить задачу
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
