package ru.alexeymalinov.taskautomation.robot.handlers;

import ru.alexeymalinov.taskautomation.core.repository.Repository;
import ru.alexeymalinov.taskautomation.core.services.RobotService;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ScheduledExecutorService;

public class OrchestratorHandler extends Handler {

    public OrchestratorHandler(List<RobotService> services, ScheduledExecutorService pool) {
        super(pool, services);
    }

    @Override
    public void run() {
        //TODO implement
        throw new IllegalStateException("Unsupported operation");
    }
}
