package ru.alexeymalinov.taskautomation.taskcreationwizard;

import ru.alexeymalinov.taskautomation.core.model.Task;
import ru.alexeymalinov.taskautomation.core.model.TaskBuilder;
import ru.alexeymalinov.taskautomation.core.repository.Repository;
import ru.alexeymalinov.taskautomation.core.services.Operation;
import ru.alexeymalinov.taskautomation.core.services.RobotService;

import java.util.Properties;
import java.util.Scanner;

public abstract class TaskCreationWizard {

    private final Repository repository;

    public TaskCreationWizard(Repository repository){
        this.repository = repository;
    }

    public Task createTask() {
        while(true) {
            switch (getStringObject("Do you want to create a task based on the existing?(Yes/No)")) {
                case "Yes":
                    return createTaskBasedOldTask();
                case "No":
                    return createNewTask();
                default:
                    System.out.println("Please try again");
                    break;
            }
        }
    }

    private Task createNewTask(){
        TaskBuilder builder = new TaskBuilder(getName());
        RobotService service = getService();
        String operation = getOperation(service);
        return builder.serviceName(service.getClass().getName())
                .operationName(operation)
                .value(getValue(operation))
//                .serverLabel(getServerLabel())
                .next(getNext())
                .create();
    }

    private Task createTaskBasedOldTask(){
        Task task = null;
        String taskName = "";
        while(true) {
            try {
                taskName = getStringObject("Please enter an existing task name");
                task = repository.getTask(taskName);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Please try again");
            }
        }
        task.setName(getStringObject("Set a new name for the task"));
        Task endTask = task;
        while(endTask.getNext() != null){
            endTask = endTask.getNext();
        }
        endTask.setNext(getNext());
        return task;
    }

    protected abstract RobotService getService();

    private String getOperation(RobotService service) {
        System.out.println("Please select a operation");
        System.out.println("You can create the following operation:");
        printOperations(service);
        boolean operationIsCorrect = false;
        while (!operationIsCorrect) {
            String operationName = new Scanner(System.in).nextLine();
            operationIsCorrect = checkOperationName(operationName, service);
            if (operationIsCorrect) {
                return operationName;
            }
            System.out.println("Operation not found");
            System.out.println("please repeat");
        }
        return "";
    }

    private void printOperations(RobotService service) {
        for (Operation operation : service.getOperations()) {
            System.out.println(operation.getName());
        }
    }

    private boolean checkOperationName(String operationName, RobotService service) {
        for (Operation operation : service.getOperations()) {
            if (operation.getName().equals(operationName)) {
                return true;
            }
        }
        return false;
    }

    protected String getValue(String operationName){
        return getStringObject("Please set value");
    }

    private String getName() {
        return getStringObject("Set task name");
    }

    private String getServerLabel() {
        return getStringObject("Set server label");
    }

    private Task getNext() {
        while (true) {
            switch(getStringObject("Would you like to add the following task?(Yes/No)")) {
                case "Yes":
                    return TaskCreationWizardFactory.getInstance(repository).getTaskMaster().createTask();
                case "No":
                    return null;
                default:
                System.out.println("please repeat");
                break;
            }
        }
    }

    public static String getStringObject(String message){
        System.out.println(message);
        return new Scanner(System.in).nextLine();
    }

}
