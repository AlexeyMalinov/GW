package ru.alexeymalinov.taskautomation.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonAppend;

public interface Task{

    public Task getNextTask();

    public void setNextTask(Task nextTask);

    public String getServerLabel();

    public void setServerLabel(String serverLabel);

}
