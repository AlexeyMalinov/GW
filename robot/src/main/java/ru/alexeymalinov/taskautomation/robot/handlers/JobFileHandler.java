package ru.alexeymalinov.taskautomation.robot.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.alexeymalinov.taskautomation.core.model.Job;
import ru.alexeymalinov.taskautomation.robot.TaskManager;
import ru.alexeymalinov.taskautomation.robot.handlers.parser.JobFileParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JobFileHandler extends Handler {

    private static final String JOB_FILE_PROPERTIES_NAME = "local.job.file.path";
    private static final Logger LOGGER = LoggerFactory.getLogger(JobFileHandler.class);

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
            LOGGER.info("schedule job: "+ job.getName());
            super.schedule(job);
        }
//        while (!Thread.currentThread().interrupted()){
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("I'am working");
//        }

    }

}
