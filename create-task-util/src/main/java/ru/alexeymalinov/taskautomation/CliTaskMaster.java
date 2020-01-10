package ru.alexeymalinov.taskautomation;

import ru.alexeymalinov.taskautomation.core.model.Task;
import ru.alexeymalinov.taskautomation.core.services.clirobotservice.CliOperation;
import ru.alexeymalinov.taskautomation.core.services.clirobotservice.CliScriptService;
import ru.alexeymalinov.taskautomation.core.services.guirobotservice.GuiOperation;

import java.util.Arrays;

public class CliTaskMaster extends TaskMaster {

    private static final String TASK_TYPE = "CliScriptTask";

    public CliTaskMaster() {
        super(CliScriptService.class.getName());
    }

    public String getTaskType() {
        return TASK_TYPE;
    }

    @Override
    public void printOperations() {
        Arrays.stream(GuiOperation.values())
                .forEach(System.out::println);
    }

    @Override
    public boolean setOperationName(String operationName) {
        for (CliOperation operation : CliOperation.values()) {
            if(operation.getName().equals(operationName)){
                super.operationName = operationName;
                return true;
            }
        }
        return false;
    }

}
