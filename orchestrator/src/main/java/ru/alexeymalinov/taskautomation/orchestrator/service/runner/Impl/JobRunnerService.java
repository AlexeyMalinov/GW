package ru.alexeymalinov.taskautomation.orchestrator.service.runner.Impl;

import com.vaadin.flow.spring.annotation.SpringComponent;
import lombok.Getter;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.*;
import ru.alexeymalinov.taskautomation.orchestrator.db.repository.RobotsGroupRepository;
import ru.alexeymalinov.taskautomation.orchestrator.db.repository.RunRepository;
import ru.alexeymalinov.taskautomation.orchestrator.service.runner.RunnerService;
import ru.alexeymalinov.taskautomation.orchestrator.service.runner.executiontask.ExecutionTask;
import ru.alexeymalinov.taskautomation.orchestrator.service.runner.executiontask.Impl.JobTask;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Getter
@SpringComponent
public class JobRunnerService implements RunnerService {

    private final Logger LOGGER = LoggerFactory.getLogger(JobRunnerService.class);
    private final RunRepository runRepository;
    private final RobotsGroupRepository robotsGroupRepository;
    private final int checkTimeout = 60000;

    public JobRunnerService(RunRepository runRepository, RobotsGroupRepository robotsGroupRepository) {
        this.runRepository = runRepository;
        this.robotsGroupRepository = robotsGroupRepository;
    }

    @Override
    public void run(PipelineElement element, RunEntity trace) {
        JobEntity job;
        if (element instanceof JobEntity) {
            job = (JobEntity) element;
        } else {
            LOGGER.info("invalid pipeline element format", new IllegalArgumentException("invalid pipeline element format"));
            return;
        }
        LOGGER.info("Start job");
        if (trace == null || job == null) return;
        trace.setJobId(job.getId());
        trace.setStartTime(LocalDateTime.now());
        LOGGER.info("Job: " + job.getName() + " group of robots id: " + job.getRobotsGroupId());
        RobotsGroupEntity robotsGroupEntity = robotsGroupRepository.findById(job.getRobotsGroupId()).get();
        LOGGER.info(robotsGroupEntity.toString());
        Set<RobotEntity> robots = robotsGroupEntity.getRobots();
        runJob(robots, trace);
    }

    @SneakyThrows
    private void runJob(Set<RobotEntity> robots, RunEntity trace) {
        ExecutorService executorService = Executors.newFixedThreadPool(robots.size());
        try {
            for (RobotEntity robot : robots) {
                ExecutionTask task = new JobTask(runRepository, trace.clone(), robot, checkTimeout);
                executorService.submit(task);
            }
        } finally {
            executorService.shutdown();
        }
    }
}
