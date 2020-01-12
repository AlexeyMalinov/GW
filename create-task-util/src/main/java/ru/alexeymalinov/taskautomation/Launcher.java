package ru.alexeymalinov.taskautomation;

import ru.alexeymalinov.taskautomation.taskcreationwizard.TaskCreationWizard;
import ru.alexeymalinov.taskautomation.taskcreationwizard.TaskCreationWizardFactory;

public class Launcher {
    public static void main(String[] args) {
        
        TaskCreationWizard master = TaskCreationWizardFactory.getInstance().getTaskMaster();
        master.createTask();
    }
}
