package ru.alexeymalinov.taskautomation;

import ru.alexeymalinov.taskautomation.core.model.Task;
import ru.alexeymalinov.taskautomation.taskcreationwizard.TaskCreationWizard;
import ru.alexeymalinov.taskautomation.taskcreationwizard.TaskCreationWizardFactory;
import ru.alexeymalinov.taskautomation.taskpublishingwizard.TaskPublishingWizard;

import java.io.*;
import java.util.Properties;

public class Launcher {

    private static final String CONFIG_FILE_PATH = "config.properties";

    public static void main(String[] args) {

        Properties properties = new Properties();
        try(Reader reader = new FileReader(new File(CONFIG_FILE_PATH))){
            properties.load(reader);
        } catch (FileNotFoundException e) {
            //TODO логирование
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        TaskPublishingWizard taskPublishingWizard = TaskPublishingWizard.getInstance(properties);
        TaskCreationWizard master = TaskCreationWizardFactory.getInstance().getTaskMaster();
        taskPublishingWizard.publishTask(master.createTask());
    }
}
