package ru.alexeymalinov.taskautomation.robot.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.alexeymalinov.taskautomation.core.model.Job;
import ru.alexeymalinov.taskautomation.core.repository.Repository;
import ru.alexeymalinov.taskautomation.core.repository.RepositoryFactory;
import ru.alexeymalinov.taskautomation.core.services.RobotService;
import ru.alexeymalinov.taskautomation.robot.handlers.parser.JobFileParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

public class JobFileHandler extends Handler {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobFileHandler.class);
    private final String jobsConfigPath;

    public JobFileHandler(List<RobotService> services, ScheduledExecutorService pool, String jobsConfigPath) {
        super(pool, services);
        this.jobsConfigPath = jobsConfigPath;
    }


    @Override
    public void run() {
        List<Job> jobs = new ArrayList<>();
        try {
            jobs = JobFileParser.parseFile(jobsConfigPath);
        } catch (IOException e) {
            LOGGER.error("Error reading file with jobs", e);
        }
        for (Job job : jobs) {
            LOGGER.info("schedule job: "+ job.getName());
            Repository repository = RepositoryFactory.getRepository(job.getRepository());
            super.schedule(job, repository);
        }
    }
}
