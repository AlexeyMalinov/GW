package ru.alexeymalinov.taskautomation.robot.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.alexeymalinov.taskautomation.core.model.Job;
import ru.alexeymalinov.taskautomation.robot.handlers.parser.JobFileParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ScheduledExecutorService;

public class JobFileHandler extends Handler {

    private static final String JOB_FILE_PROPERTIES_NAME = "local.job.file.path";
    private static final Logger LOGGER = LoggerFactory.getLogger(JobFileHandler.class);

    public JobFileHandler(Properties properties, ScheduledExecutorService pool) {
        super(properties, pool);
    }


    @Override
    public void run() {
        List<Job> jobs = new ArrayList<>();
        try {
            jobs = JobFileParser.parseFile(super.getProperties().getProperty(JOB_FILE_PROPERTIES_NAME));
        } catch (IOException e) {
            LOGGER.error("Error reading file with jobs", e);
        }
        for (Job job : jobs) {
            LOGGER.info("schedule job: "+ job.getName());
            super.schedule(job);
        }
    }
}
