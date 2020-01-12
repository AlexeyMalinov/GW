package ru.alexeymalinov.taskautomation;

public class Launcher {
    public static void main(String[] args) {
        TaskCreationWizard master = TaskMasterFactory.getInstance().getTaskMaster();
        master.createTask();
    }
}
