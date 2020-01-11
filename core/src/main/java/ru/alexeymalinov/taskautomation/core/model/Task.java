package ru.alexeymalinov.taskautomation.core.model;

import java.util.Objects;

public class Task{

    private Task next;
    private String name;
    private String ServerLabel;
    private String serviceName;
    private String value;
    private String operationName;

    public Task() {
    }

    public Task(Task nextTask, String name, String serverLabel, String serviceName, String value) {
        this.name = name;
        this.next = nextTask;
        ServerLabel = serverLabel;
        this.serviceName = serviceName;
        this.value = value;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Task getNext() {
        return next;
    }

    public void setNext(Task next) {
        this.next = next;
    }

    public String getServerLabel() {
        return ServerLabel;
    }

    public void setServerLabel(String serverLabel) {
        ServerLabel = serverLabel;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public TaskBuilder builder(String name){
        return new TaskBuilder(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(next, task.next) &&
                Objects.equals(name, task.name) &&
                Objects.equals(ServerLabel, task.ServerLabel) &&
                Objects.equals(serviceName, task.serviceName) &&
                Objects.equals(value, task.value) &&
                Objects.equals(operationName, task.operationName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(next, name, ServerLabel, serviceName, value, operationName);
    }

    @Override
    public String toString() {
        return "Task{" +
                "next=" + next +
                ", name='" + name + '\'' +
                ", ServerLabel='" + ServerLabel + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", value='" + value + '\'' +
                ", operationName='" + operationName + '\'' +
                '}';
    }
}
