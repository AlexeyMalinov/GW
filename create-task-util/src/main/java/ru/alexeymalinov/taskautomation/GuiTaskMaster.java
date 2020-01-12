package ru.alexeymalinov.taskautomation;

import ru.alexeymalinov.taskautomation.core.services.RobotService;
import ru.alexeymalinov.taskautomation.core.services.guirobotservice.GuiScriptService;

import java.awt.*;
import java.util.Scanner;

public class GuiTaskMaster extends TaskMaster{

    private RobotService service = new GuiScriptService();

    @Override
    protected String getValue(String operation) {
        if("point".equals(operation)){
            try {
                Robot robot = new Robot();
                System.out.println("Please move the cursor to the desired point and press Enter");
                new Scanner(System.in).nextLine();
                return getMousePoint();
            } catch (AWTException e) {
                //TODO логирование
                e.printStackTrace();
            }
        }
        return getStringObject("Please set value");
    }

    @Override
    protected RobotService getService() {
        return service;
    }

    private static String getMousePoint() {
        Point point = MouseInfo.getPointerInfo().getLocation();
        return point.x + ";" + point.y;
    }


}
