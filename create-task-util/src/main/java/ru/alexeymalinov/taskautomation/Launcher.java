package ru.alexeymalinov.taskautomation;

public class Launcher {
    public static void main(String[] args) {
        TaskMaster master = new TaskMaster();
        master.createTask();
    }
}
