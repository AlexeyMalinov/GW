package ru.alexeymalinov.taskautomation.core.model;

public class CliScriptTask implements Task {
    private Task nextTask;
    private String serverLabel;
    private String script;

    public CliScriptTask(){}

    public CliScriptTask(Task nextTask, String serverLabel, String script) {
        this.nextTask = nextTask;
        this.serverLabel = serverLabel;
        this.script = script;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
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
