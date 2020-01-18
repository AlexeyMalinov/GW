package ru.alexeymalinov.taskautomation.taskcreationwizard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.alexeymalinov.taskautomation.core.repository.Repository;
import ru.alexeymalinov.taskautomation.core.services.RobotService;
import ru.alexeymalinov.taskautomation.core.services.clirobotservice.CliScriptService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Scanner;

public class CliTaskCreationWizard extends TaskCreationWizard {

    private static final Logger LOGGER = LoggerFactory.getLogger(CliTaskCreationWizard.class);
    private RobotService service;

    public CliTaskCreationWizard(Repository repository) {
        super(repository);
        service = new CliScriptService();
    }

    @Override
    protected RobotService getService() {
        return service;
    }

    @Override
    protected String getValue(String operation) {
        if ("EXECUTE_SCRIPT".equals(operation)) {
            while (true) {
                try {
                    System.out.println("Please enter the path to the file");
                    return readScriptFile(new Scanner(System.in).nextLine());
                } catch (IOException e) {
                    //TODO логирование
                    e.printStackTrace();
                    System.out.println("Please try again");
                }
            }
        }
        return getStringObject("Please set value");
    }

    private String readScriptFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        LOGGER.debug("Script: " + Files.readString(path, StandardCharsets.UTF_8));
        return path.getFileName().toString() + System.lineSeparator() + Files.readString(path,StandardCharsets.UTF_8);
    }

}
