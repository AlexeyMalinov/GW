package ru.alexeymalinov.taskautomation;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskMasterFactory {

    private List<TaskCreationWizard> taskCreationWizardList = new ArrayList<>();

    private TaskMasterFactory(){
        initializeTaskMasterList();
    }
    private void initializeTaskMasterList(){
        taskCreationWizardList.add(new GuiTaskCreationWizard());
        taskCreationWizardList.add(new CliTaskCreationWizard());
    }

    public static TaskMasterFactory getInstance(){
        return new TaskMasterFactory();
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
