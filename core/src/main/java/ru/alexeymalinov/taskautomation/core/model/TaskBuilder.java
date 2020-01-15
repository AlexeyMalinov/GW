package ru.alexeymalinov.taskautomation.core.model;

public class TaskBuilder {

    private final String name;
    private String serviceName;
    private String operationName;
    private String value;
    private Task next;
    private String serverLabel;

    public TaskBuilder(String name) {
        this.name = name;
    }

    public TaskBuilder serviceName(String serviceName){
        this.serviceName = serviceName;
        return this;
    }

    public TaskBuilder operationName(String operationName){
        this.operationName = operationName;
        return this;
    }

    public TaskBuilder value(String value){
        this.value = value;
        return this;
    }

    public TaskBuilder next(Task nextTask){
        next = nextTask;
        return this;
    }

    public TaskBuilder serverLabel(String serverLabel){
        this.serverLabel = serverLabel;
        return this;
    }

    public Task create(){
        return new Task(next, name, serverLabel, serviceName, operationName, value);
    }
}
