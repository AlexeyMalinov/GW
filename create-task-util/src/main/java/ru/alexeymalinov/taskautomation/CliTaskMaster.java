package ru.alexeymalinov.taskautomation;

import ru.alexeymalinov.taskautomation.core.services.RobotService;
import ru.alexeymalinov.taskautomation.core.services.clirobotservice.CliScriptService;

public class CliTaskMaster extends TaskMaster {

    private RobotService service = new CliScriptService();

    @Override
    protected RobotService getService() {
        return service;
    }


}
