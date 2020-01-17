package ru.alexeymalinov.taskautomation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.alexeymalinov.taskautomation.core.model.Task;
import ru.alexeymalinov.taskautomation.core.repository.Repository;
import ru.alexeymalinov.taskautomation.core.repository.RepositoryFactory;
import ru.alexeymalinov.taskautomation.core.repository.RepositoryType;
import ru.alexeymalinov.taskautomation.taskcreationwizard.TaskCreationWizard;
import ru.alexeymalinov.taskautomation.taskcreationwizard.TaskCreationWizardFactory;
import ru.alexeymalinov.taskautomation.taskpublishingwizard.TaskPublishingWizard;

import java.io.*;
import java.util.Properties;

public class Launcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

    private static final String FILE_NOT_FOUND_EXCEPTION = "No configuration file found";
    private static final String CONFIG_FILE_PATH = "config.properties";
    private static final String IO_CONFIG_FILE_EXCEPTION = "error reading configuration file";

    public static void main(String[] args) {

        Properties properties = null;
        try {
            properties = getProperties(CONFIG_FILE_PATH);
        } catch (FileNotFoundException e) {
            LOGGER.error(FILE_NOT_FOUND_EXCEPTION, e);
        } catch (IOException e) {
            LOGGER.error(IO_CONFIG_FILE_EXCEPTION, e);
        }
        Repository repository = getRepository(properties);
        TaskPublishingWizard taskPublishingWizard = TaskPublishingWizard.getInstance(repository);
        TaskCreationWizard master = TaskCreationWizardFactory.getInstance(properties, repository).getTaskMaster();
        Task task = master.createTask();
        boolean taskCreated = taskPublishingWizard.publishTask(task);
        if (!taskCreated) {
            deleteOrRenameTask(task, taskPublishingWizard);
        }
    }

    private static Repository getRepository(Properties properties) {
        String reposType = TaskCreationWizard.getStringObject("Please select a repository type (LOCAL/REMOTE)");
        while (true) {
            switch (reposType) {
                case "LOCAL":
                    return new RepositoryFactory().getRepository(RepositoryType.LOCAL, properties);
                case "REMOTE":
                    return new RepositoryFactory().getRepository(RepositoryType.REMOTE, properties);
                default:
                    reposType = TaskCreationWizard.getStringObject("Please try again");
            }
        }
    }

    private static Properties getProperties(String propertiesFilePath) throws IOException {
        Properties properties = new Properties();
        try (Reader reader = new FileReader(new File(CONFIG_FILE_PATH))) {
            properties.load(reader);
        }
        return properties;
    }

    private static void deleteOrRenameTask(Task task, TaskPublishingWizard wizard) {
        boolean exit = false;
        while (!exit) {
            String answer = TaskCreationWizard.getStringObject(
                    "If you want to delete the old task, enter DELETE\n"
                            + "If you want to rename a new task, enter RENAME\n"
                            + "To terminate the application without saving the task, enter EXIT\n"
            );
            switch (answer) {
                case "DELETE":
                    wizard.deleteTask(task.getName());
                    exit = wizard.publishTask(task);
                    break;
                case "RENAME":
                    task.setName(TaskCreationWizard.getStringObject("Set task name"));
                    exit = wizard.publishTask(task);
                    break;
                case "EXIT":
                    exit = true;
                    break;
                default:
                    System.out.println("Please try again");
                    break;
            }
        }
    }
}
