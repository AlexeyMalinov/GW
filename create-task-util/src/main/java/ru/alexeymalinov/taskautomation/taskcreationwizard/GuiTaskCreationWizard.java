package ru.alexeymalinov.taskautomation.taskcreationwizard;

import ru.alexeymalinov.taskautomation.core.repository.Repository;
import ru.alexeymalinov.taskautomation.core.services.RobotService;
import ru.alexeymalinov.taskautomation.core.services.guirobotservice.GuiScriptService;

import java.awt.*;
import java.util.Properties;
import java.util.Scanner;

public class GuiTaskCreationWizard extends TaskCreationWizard {

    private RobotService service = new GuiScriptService();

    public GuiTaskCreationWizard(Properties properties, Repository repository) {
        super(properties, repository);
    }

    @Override
    protected String getValue(String operation) {
        if("POINT".equals(operation)){
            try {
                Robot robot = new Robot();
                System.out.println("Please move the cursor to the desired point and press Enter");
                new Scanner(System.in).nextLine();
                return getMousePoint();
            } catch (AWTException e) {
                //TODO логирование
                e.printStackTrace();
            }
        } else if(operation.contains("LEFT") || operation.contains("RIGHT") || operation.contains("CLICK")){
            return "";
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
