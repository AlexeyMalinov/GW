package ru.alexeymalinov.taskautomation.robot.handlers;

import ru.alexeymalinov.taskautomation.core.model.Job;
import ru.alexeymalinov.taskautomation.core.model.Task;
import ru.alexeymalinov.taskautomation.core.repository.Repository;
import ru.alexeymalinov.taskautomation.core.repository.RepositoryFactory;
import ru.alexeymalinov.taskautomation.core.repository.RepositoryType;
import ru.alexeymalinov.taskautomation.robot.TaskManager;
import ru.alexeymalinov.taskautomation.robot.handlers.parser.JobFileParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JobFileHandler extends Handler {

    private static final String JOB_FILE_PROPERTIES_NAME = "local.job.file.path";

    public JobFileHandler(TaskManager taskManager, Properties properties) {
        super(taskManager, properties);
    }


    @Override
    public void run() {
        List<Job> jobs = new ArrayList<>();
        try {
            jobs = JobFileParser.parseFile(super.getProperties().getProperty(JOB_FILE_PROPERTIES_NAME));
        } catch (IOException e) {
            //TODO логирование
            e.printStackTrace();
        }
        for (Job job : jobs) {
            super.schedule(job);
        }

    }

}
