package ru.alexeymalinov.taskautomation.core.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo()
public class GuiScriptTask implements Task {
    private Task nextTask;
    private String serverLabel;
    private String commandName;
    private String data;

    public GuiScriptTask(){}

    public GuiScriptTask(Task nextTask, String serverLabel, String commandName, String data) {
        this.nextTask = nextTask;
        this.serverLabel = serverLabel;
        this.commandName = commandName;
        this.data = data;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public Task getNextTask() {
        return nextTask;
    }

    @Override
    public void setNextTask(Task nextTask) {
        this.nextTask = nextTask;
    }

    @Override
    public String getServerLabel() {
        return serverLabel;
    }

    @Override
    public void setServerLabel(String serverLabel) {
        this.serverLabel = serverLabel;
    }
}
