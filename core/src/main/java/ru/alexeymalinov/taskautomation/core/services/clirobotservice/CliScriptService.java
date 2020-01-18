package ru.alexeymalinov.taskautomation.core.services.clirobotservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.alexeymalinov.taskautomation.core.model.Task;
import ru.alexeymalinov.taskautomation.core.services.Operation;
import ru.alexeymalinov.taskautomation.core.services.RobotService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

public class CliScriptService implements RobotService {

    private final static Logger LOGGER = LoggerFactory.getLogger(CliScriptService.class);
    private final Runtime RUNTIME = Runtime.getRuntime();
    private Operation<Runtime>[] operations = CliOperation.values();
    private final String workspace;

    public CliScriptService(){
        this.workspace = "./";
    }
    public CliScriptService(String workspace) {
        this.workspace = workspace;
    }

    @Override
    public void notifyService(Task task) {
        if (task == null) return;
        if (checkTask(task)) {
            LOGGER.info("start task: " + task.getName() + ", with operation: " + task.getOperationName());
            runTask(task);
            LOGGER.info("completed task: " + task.getName() + ", with operation: " + task.getOperationName());
        }
    }

    @Override
    public Operation[] getOperations() {
        return operations;
    }

    private void runTask(Task task) {

        Operation<Runtime> operation = Arrays.stream(operations)
                .filter((v) -> v.getName().equals(task.getOperationName()))
                .findFirst()
                .orElseThrow(() -> {
                    RuntimeException e = new IllegalStateException("operation not supported");
                    LOGGER.error("operation not supported", e);
                    return e;
                });
        if (operation.getName().equals("EXECUTE_SCRIPT")) {
            try {
                task.setValue(writeScriptToFileInWorkspace(task.getValue()));
            } catch (IOException e) {
                LOGGER.error("When executing the \"EXECUTE_SCRIPT\" command, the script file could not be prepared", e);
            }
        }
        LOGGER.info("operation: " + operation + " started");
        operation.apply(RUNTIME, task.getValue());
        LOGGER.info("operation: " + operation + " completed");
    }

    private String writeScriptToFileInWorkspace(String string) throws IOException {
        LOGGER.debug("String: " + string);
        String[] lines = string.split(System.lineSeparator());
        StringBuilder script = new StringBuilder();
        for (int i = 1; i < lines.length; i++) {
            script.append(lines[i]);
            if (i == lines.length - 1) {
                script.append(System.lineSeparator());
            }
        }
        LOGGER.debug("Script: " + script.toString());
        String fileName = lines[0];
        LOGGER.debug("File name: " + fileName);
        LOGGER.debug("workspace.path: " + workspace);
        Path path = Paths.get(workspace + "/" + fileName);
        LOGGER.debug("Path to save the file: " + path.toAbsolutePath().toString());
        Files.writeString(path, script.toString(), StandardOpenOption.CREATE);
        return path.toAbsolutePath().toString();
    }
}
