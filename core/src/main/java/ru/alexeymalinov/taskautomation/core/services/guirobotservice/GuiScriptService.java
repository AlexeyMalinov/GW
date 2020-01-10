package ru.alexeymalinov.taskautomation.core.services.guirobotservice;

import ru.alexeymalinov.taskautomation.core.model.Task;
import ru.alexeymalinov.taskautomation.core.services.RobotService;

import java.awt.*;

public class GuiScriptService implements RobotService {

    private static Robot robot;

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

    private void runTask(Task task){
        throw new IllegalStateException("operation not supported");
    }
}
