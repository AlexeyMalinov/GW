package ru.alexeymalinov.taskautomation.core.services.clirobotservice;

import ru.alexeymalinov.taskautomation.core.model.Task;
import ru.alexeymalinov.taskautomation.core.services.Operation;
import ru.alexeymalinov.taskautomation.core.services.RobotService;

import java.io.IOException;
import java.util.Arrays;

public class CliScriptService implements RobotService {

    private final static Runtime RUNTIME = Runtime.getRuntime();
    Operation<Runtime>[] operations = CliOperation.values();


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

    @Override
    public Operation[] getOperations() {
        return operations;
    }

    private void runTask(Task task) throws IOException {
        Operation<Runtime> operation= Arrays.stream(operations)
                .filter((v) -> v.getName().equals(task.getOperationName()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("operation not supported"));
        operation.apply(RUNTIME, task.getValue());
    }
}
