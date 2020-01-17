package ru.alexeymalinov.taskautomation.robot.handlers;

import java.util.Properties;
import java.util.concurrent.ScheduledExecutorService;

public class OrchestratorHandler extends Handler {

    public OrchestratorHandler(Properties properties, ScheduledExecutorService pool) {
        super(properties, pool);
    }

    @Override
    public void run() {
        //TODO implement
        throw new IllegalStateException("Unsupported operation");
    }
}
