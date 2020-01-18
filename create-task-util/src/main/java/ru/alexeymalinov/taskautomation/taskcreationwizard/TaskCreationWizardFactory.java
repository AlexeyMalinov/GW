package ru.alexeymalinov.taskautomation.taskcreationwizard;

import ru.alexeymalinov.taskautomation.core.repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class TaskCreationWizardFactory {

    private List<TaskCreationWizard> taskCreationWizardList = new ArrayList<>();

    private TaskCreationWizardFactory(Repository repository){
        initializeTaskMasterList(repository);
    }
    private void initializeTaskMasterList(Repository repository){
        taskCreationWizardList.add(new GuiTaskCreationWizard(repository));
        taskCreationWizardList.add(new CliTaskCreationWizard(repository));
    }

    public static TaskCreationWizardFactory getInstance(Repository repository){
        return new TaskCreationWizardFactory(repository);
    }

    public TaskCreationWizard getTaskMaster(){
        System.out.println("Please select a task");
        System.out.println("You can create the following tasks:");
        for (TaskCreationWizard master : taskCreationWizardList) {
            System.out.println(master.getService().getClass().getSimpleName());
        }
        TaskCreationWizard master = null;
        while (master == null) {
            String serviceName = new Scanner(System.in).nextLine();
            master = serviceOfName(serviceName);
            if (master != null) {
                return master;
            }
            System.out.println("Task type not found");
            System.out.println("please repeat");
        }
        return null;
    }

    private TaskCreationWizard serviceOfName(String name) {
        for (TaskCreationWizard taskCreationWizard : taskCreationWizardList) {
            if (taskCreationWizard.getService().getClass().getSimpleName().equals(name)) {
                return taskCreationWizard;
            }
        }
        return null;
    }

}
