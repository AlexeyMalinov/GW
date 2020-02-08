package ru.alexeymalinov.taskautomation.core.services.guirobotservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.alexeymalinov.taskautomation.core.model.Task;
import ru.alexeymalinov.taskautomation.core.services.Operation;
import ru.alexeymalinov.taskautomation.core.services.RobotService;

import java.awt.*;
import java.util.Arrays;

public class GuiScriptService implements RobotService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GuiScriptService.class);
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
    public void execute(Task task) {
        if (task == null) return;
        if (checkTask(task)) {
            LOGGER.info("start task: " + task.getName() + ", with operation: " + task.getOperationName());
            runTask(task);
            LOGGER.info("completed task: " + task.getName() + ", with operation: " + task.getOperationName());
        }
    }

    @Override
    public Operation[] getOperations() {
        return operations;
    }

    private void runTask(Task task) {
        Operation<Robot> operation = Arrays.stream(operations)
                .filter((v) -> v.getName().equals(task.getOperationName()))
                .findFirst()
                .orElseThrow(() -> {
                    RuntimeException e = new IllegalStateException("operation not supported");
                    LOGGER.error("operation not supported", e);
                    return e;
                });
        LOGGER.info("operation: " + operation + " started");
        operation.apply(robot, task.getValue());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOGGER.info("operation: " + operation + " completed");
    }
}
