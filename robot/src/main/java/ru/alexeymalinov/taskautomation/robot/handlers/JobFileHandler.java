package ru.alexeymalinov.taskautomation.robot.handlers;

import ru.alexeymalinov.taskautomation.robot.TaskManager;

import java.io.File;
import java.util.Properties;

public class JobFileHandler extends Handler {

    private final File taskFile;

    public JobFileHandler(TaskManager taskManager, Properties properties) {
        super(taskManager);
        taskFile = new File(properties.getProperty("local.task.file.path"));
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){
            System.out.println("Проверяю файл с задачами");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
