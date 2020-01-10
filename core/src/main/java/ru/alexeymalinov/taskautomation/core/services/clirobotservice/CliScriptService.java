package ru.alexeymalinov.taskautomation.core.services.clirobotservice;

import ru.alexeymalinov.taskautomation.core.model.Task;
import ru.alexeymalinov.taskautomation.core.services.RobotService;

import java.io.IOException;

public class CliScriptService implements RobotService {

    private final static Runtime RUNTIME = Runtime.getRuntime();


    @Override
    public void notifyService(Task task) {
        if(task == null) return;
        if(checkTask(task)){
            try {
                runTask(task);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void runTask(Task task) throws IOException {
        RUNTIME.exec(task.getValue());
        throw new IllegalStateException("operation not supported");
    }
}
