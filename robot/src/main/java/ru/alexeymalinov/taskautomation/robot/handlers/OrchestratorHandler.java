package ru.alexeymalinov.taskautomation.robot.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.alexeymalinov.taskautomation.core.model.Job;
import ru.alexeymalinov.taskautomation.core.model.JobStatus;
import ru.alexeymalinov.taskautomation.core.repository.Repository;
import ru.alexeymalinov.taskautomation.core.repository.RepositoryFactory;
import ru.alexeymalinov.taskautomation.core.services.RobotService;
import ru.alexeymalinov.taskautomation.robot.TaskExecutor;
import ru.alexeymalinov.taskautomation.robot.connection.OrchestratorConnection;
import ru.alexeymalinov.taskautomation.robot.executor.TaskExecutorFactory;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

public class OrchestratorHandler extends Handler {

    private final Logger LOGGER = LoggerFactory.getLogger(OrchestratorHandler.class);
    private final OrchestratorConnection connection;
    private JobStatus jobStatus = JobStatus.READY;

    public OrchestratorHandler(List<RobotService> services, ScheduledExecutorService pool, Integer robotId, String orchestratorUrl) {
        super(pool, services);
        connection = OrchestratorConnection.getInstance(robotId, orchestratorUrl);
    }

    public OrchestratorHandler(List<RobotService> services, ScheduledExecutorService pool, Integer robotId, String orchestratorUrl, JobStatus jobStatus) {
        this(services, pool, robotId, orchestratorUrl);
        this.jobStatus = jobStatus;
    }

    @Override
    public void run() {
        LOGGER.info("Getting a list of jobs with status: " + jobStatus.name());
        List<Job> jobs = connection.getJobsByJobStatus(jobStatus);
        if (jobs == null || jobs.isEmpty()) {
            LOGGER.info("No new jobs");
        }
        for (Job job : jobs) {
            LOGGER.info("schedule job: " + job.getName());
            schedule(job);
        }
    }

    @Override
    protected TaskExecutor getTaskExecutor(Job job, Repository repository, List<RobotService> services) {
        return TaskExecutorFactory.getTaskExecutor(job, getTask(job, repository), services, connection);
    }
}
