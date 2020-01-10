package ru.alexeymalinov.taskautomation.core.services.guirobotservice;

import ru.alexeymalinov.taskautomation.core.model.GuiScriptTask;
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
        if(checkTask(task)){}
    }

    @Override
    public boolean checkTask(Task task){
        return task instanceof GuiScriptTask;
    }

    private void runTask(Task task){
        throw new IllegalStateException("operation not supported");
    }
}
