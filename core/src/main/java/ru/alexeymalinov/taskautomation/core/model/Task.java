package ru.alexeymalinov.taskautomation.core.model;

import java.util.Objects;

public class Task{

    private Task nextTask;
    private String ServerLabel;
    private String serviceName;
    private String value;
    private String operationName;

    public Task() {
    }

    public Task(Task nextTask, String serverLabel, String serviceName, String value) {
        this.nextTask = nextTask;
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


    public Task getNextTask() {
        return nextTask;
    }

    public void setNextTask(Task nextTask) {
        this.nextTask = nextTask;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(nextTask, task.nextTask) &&
                Objects.equals(ServerLabel, task.ServerLabel) &&
                Objects.equals(serviceName, task.serviceName) &&
                Objects.equals(value, task.value) &&
                Objects.equals(operationName, task.operationName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nextTask, ServerLabel, serviceName, value, operationName);
    }

    @Override
    public String toString() {
        return "Task{" +
                "nextTask=" + nextTask +
                ", ServerLabel='" + ServerLabel + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", value='" + value + '\'' +
                ", operationName='" + operationName + '\'' +
                '}';
    }
}
