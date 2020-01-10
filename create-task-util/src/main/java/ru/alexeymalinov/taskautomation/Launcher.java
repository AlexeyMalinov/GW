package ru.alexeymalinov.taskautomation;

import ru.alexeymalinov.taskautomation.core.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Launcher {

    public static void main(String[] args) {

    }

    private Task createTask(){
        TaskMasterFactory masterFactory = TaskMasterFactory.getInstance(initializeMasters());
        TaskMaster master = getMaster(masterFactory);
        setOperation(master);
        setValue(master);

        return master.createTask();
    }

    private static List<TaskMaster> initializeMasters() {
        List<TaskMaster> masters = new ArrayList<TaskMaster>();
        masters.add(new CliTaskMaster());
        masters.add(new GuiTaskMaster());
        return masters;
    }

    private static TaskMaster getMaster(TaskMasterFactory masterFactory) {
        System.out.println("Please select a task");
        System.out.println("You can create the following tasks:");
        masterFactory.printTypeTask();
        TaskMaster master = null;
        while (master == null) {
            master = masterFactory.getInstance(new Scanner(System.in).nextLine());
            if (master == null) {
                System.out.println("Task type not found");
                System.out.println("please repeat");
            }
        }
        return master;
    }

    private static TaskMaster setOperation(TaskMaster master) {
        System.out.println("Please select a operation");
        System.out.println("You can create the following operation:");
        master.printOperations();
        boolean operationIsSet = false;
        while (operationIsSet) {
            operationIsSet = master.setOperationName(new Scanner(System.in).nextLine());
            System.out.println("Operation not found");
            System.out.println("please repeat");
        }
        return master;
    }

    private static TaskMaster setValue(TaskMaster master){
        System.out.println("Set argument for operation");
        master.setValue(new Scanner(System.in).nextLine());
        return master;
    }

    private static TaskMaster setName(TaskMaster master){
        System.out.println("Set task name");
        master.setName(new Scanner(System.in).nextLine());
        return master;
    }

    private static TaskMaster setServerLabel(TaskMaster master){
        System.out.println("Set server label");
        master.setServerLabel(new Scanner(System.in).nextLine());
        return master;
    }
}
