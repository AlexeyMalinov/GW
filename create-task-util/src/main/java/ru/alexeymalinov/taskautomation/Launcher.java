package ru.alexeymalinov.taskautomation;

public class Launcher {
    public static void main(String[] args) {
        TaskMaster master = TaskMasterFactory.getInstance().getTaskMaster();
        master.createTask();
    }
}
