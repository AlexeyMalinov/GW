package ru.alexeymalinov.taskautomation.core.services.guirobotservice;

import ru.alexeymalinov.taskautomation.core.model.Task;
import ru.alexeymalinov.taskautomation.core.services.Operation;
import ru.alexeymalinov.taskautomation.core.services.RobotService;

import java.awt.*;
import java.util.Arrays;

public class GuiScriptService implements RobotService {

    private static Robot robot;
    private static Operation<Robot>[] operations = GuiOperation.values();

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void notifyService(Task task) {
        if(task == null) return;
        if(checkTask(task)){
            runTask(task);
        }
    }

    @Override
    public Operation[] getOperations() {
        return operations;
    }

    private void runTask(Task task){
        Operation<Robot> operation= Arrays.stream(operations)
                .filter((v) -> v.getName().equals(task.getOperationName()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("operation not supported"));
        operation.apply(robot, task.getValue());
    }
}
