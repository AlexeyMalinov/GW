package ru.alexeymalinov.taskautomation.robot.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.alexeymalinov.taskautomation.core.model.Job;
import ru.alexeymalinov.taskautomation.core.model.JobStatus;
import ru.alexeymalinov.taskautomation.core.model.Task;
import ru.alexeymalinov.taskautomation.core.services.RobotService;
import ru.alexeymalinov.taskautomation.robot.TaskExecutor;
import ru.alexeymalinov.taskautomation.robot.connection.OrchestratorConnection;

import java.util.List;

public class OrchestratorTaskExecutor implements TaskExecutor {

    private final Logger LOGGER = LoggerFactory.getLogger(OrchestratorConnection.class);
    private final OrchestratorConnection connection;
    private final List<RobotService> services;
    private final Job job;
    private final Task task;

    public OrchestratorTaskExecutor(OrchestratorConnection connection, Job job, List<RobotService> services, Task task) {
        this.connection = connection;
        this.services = services;
        this.job = job;
        this.task = task;

    }

    @Override
    public void run() {
        Task currentTask = task;
        try {
            while (currentTask != null) {
                startTask(currentTask, services);
                LOGGER.debug("get Next task : " + task.getNext());
                currentTask = currentTask.getNext();
            }
            job.setStatus(JobStatus.SUCCESS.name());
        } catch (Exception e) {
            job.setStatus(JobStatus.FAILURE.name());
        } finally {
            LOGGER.debug("TaskExecutor has finished work with status: " + job.getStatus());
            sendStatus();
            LOGGER.debug("Send status");
        }
    }

    private void sendStatus(){
        connection.setJobStatus(job);
    }
}
