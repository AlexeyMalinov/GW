package ru.alexeymalinov.taskautomation.taskcreationwizard;

import ru.alexeymalinov.taskautomation.core.services.RobotService;
import ru.alexeymalinov.taskautomation.core.services.clirobotservice.CliScriptService;

public class CliTaskCreationWizard extends TaskCreationWizard {

    private RobotService service = new CliScriptService();

    @Override
    protected RobotService getService() {
        return service;
    }


}
