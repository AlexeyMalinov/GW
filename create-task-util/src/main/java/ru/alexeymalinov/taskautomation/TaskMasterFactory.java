package ru.alexeymalinov.taskautomation;

import ru.alexeymalinov.taskautomation.core.services.RobotService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskMasterFactory {

    private List<TaskMaster> taskMasterList = new ArrayList<>();

    private TaskMasterFactory(){
        initializeTaskMasterList();
    }
    private void initializeTaskMasterList(){
        taskMasterList.add(new GuiTaskMaster());
        taskMasterList.add(new CliTaskMaster());
    }

    public static TaskMasterFactory getInstance(){
        return new TaskMasterFactory();
    }

    public TaskMaster getTaskMaster(){
        System.out.println("Please select a task");
        System.out.println("You can create the following tasks:");
        for (TaskMaster master : taskMasterList) {
            System.out.println(master.getService().getClass().getSimpleName());
        }
        TaskMaster master = null;
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

    private TaskMaster serviceOfName(String name) {
        for (TaskMaster taskMaster : taskMasterList) {
            if (taskMaster.getService().getClass().getSimpleName().equals(name)) {
                return taskMaster;
            }
        }
        return null;
    }

}
