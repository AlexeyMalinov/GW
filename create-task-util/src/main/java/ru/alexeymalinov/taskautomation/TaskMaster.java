package ru.alexeymalinov.taskautomation;

import ru.alexeymalinov.taskautomation.core.model.Task;

public abstract class TaskMaster {

    private final String serviceName;
    private String name;
    private String value;
    private Task next;
    private String serverLabel;
    protected String operationName;

    TaskMaster(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Task getNext() {
        return next;
    }

    public void setNext(Task next) {
        this.next = next;
    }

    public String getOperationName() {
        return operationName;
    }

    public String getServerName() {
        return serverLabel;
    }

    public void setServerLabel(String serverLabel) {
        this.serverLabel = serverLabel;
    }


    /**
     * Создает задачу
     *
     * @return
     */
    public Task createTask() {
        return new Task(next, name, serverLabel, serviceName, value);
    }

    /**
     * Возвращает тип задачи
     *
     * @return
     */
    public abstract String getTaskType();

    /**
     * Распечатывает в консоль все доступные операции
     */
    public abstract void printOperations();

    /**
     * Задает знаяение операции
     * @param name
     * @return
     */
    public abstract boolean setOperationName(String name);

}
