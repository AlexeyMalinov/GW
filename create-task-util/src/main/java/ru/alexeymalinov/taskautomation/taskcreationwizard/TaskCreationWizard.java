package ru.alexeymalinov.taskautomation.taskcreationwizard;

import ru.alexeymalinov.taskautomation.core.model.Task;
import ru.alexeymalinov.taskautomation.core.model.TaskBuilder;
import ru.alexeymalinov.taskautomation.core.services.Operation;
import ru.alexeymalinov.taskautomation.core.services.RobotService;
import ru.alexeymalinov.taskautomation.core.services.clirobotservice.CliScriptService;
import ru.alexeymalinov.taskautomation.core.services.guirobotservice.GuiScriptService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class TaskCreationWizard {

    private List<RobotService> services = new ArrayList<>();

    private void initializeService() {
        services.add(new CliScriptService());
        services.add(new GuiScriptService());
    }

    public Task createTask() {
        initializeService();
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

    protected abstract RobotService getService();

    private RobotService serviceOfName(String name) {
        for (RobotService service : services) {
            if (service.getClass().getSimpleName().equals(name)) {
                return service;
            }
        }
        return null;
    }

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
        System.out.println("Would you like to add the following task?(Yes/No)");
        String answer = "";
        while (answer.isEmpty()) {
            answer = new Scanner(System.in).nextLine();
            if ("Yes".equals(answer)) {
                break;
            } else if ("No".equals(answer)) {
                return null;
            }
            System.out.println("please repeat");
        }
        TaskCreationWizard master = TaskCreationWizardFactory.getInstance().getTaskMaster();
        return master.createTask();
    }

    protected String getStringObject(String message){
        System.out.println(message);
        return new Scanner(System.in).nextLine();
    }

}
