package ru.alexeymalinov.taskautomation;

import ru.alexeymalinov.taskautomation.core.model.Task;
import ru.alexeymalinov.taskautomation.core.services.guirobotservice.GuiOperation;
import ru.alexeymalinov.taskautomation.core.services.guirobotservice.GuiScriptService;

import java.util.Arrays;

public class GuiTaskMaster extends TaskMaster {

    private static final String TASK_TYPE = "GuiScriptTask";

    public GuiTaskMaster() {
        super(GuiScriptService.class.getName());
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
        for (GuiOperation operation : GuiOperation.values()) {
            if(operation.getName().equals(operationName)){
                super.operationName = operationName;
                return true;
            }
        }
        return false;
    }
}
