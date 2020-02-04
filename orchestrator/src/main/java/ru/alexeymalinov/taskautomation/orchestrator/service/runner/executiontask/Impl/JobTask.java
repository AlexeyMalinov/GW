package ru.alexeymalinov.taskautomation.orchestrator.service.runner.executiontask.Impl;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.alexeymalinov.taskautomation.core.model.JobStatus;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.RobotEntity;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.RunEntity;
import ru.alexeymalinov.taskautomation.orchestrator.db.repository.RunRepository;
import ru.alexeymalinov.taskautomation.orchestrator.service.runner.executiontask.ExecutionTask;

import java.util.List;
import java.util.stream.Collectors;

public class JobTask implements ExecutionTask {

    private final Logger LOGGER = LoggerFactory.getLogger(JobTask.class);
    private final int checkTimeout;
    private final RunRepository runRepository;
    private final RobotEntity robot;
    private RunEntity trace;

    public JobTask(RunRepository runRepository, RunEntity trace, RobotEntity robot, int checkTimeout) {
        this.checkTimeout = checkTimeout;
        this.runRepository = runRepository;
        this.trace = trace;
        this.robot = robot;
    }

    @SneakyThrows
    @Override
    public void run() {
        trace.setRobotId(robot.getId());
        List<RunEntity> runEntityList = runRepository.findByUid(trace.getUid());
        List<RunEntity> runsList = runEntityList.stream()
                .filter(r -> trace.getPipelineId().equals(r.getPipelineId()))
                .filter(r -> trace.getStageId().equals(r.getStageId()))
                .filter(r -> trace.getJobId().equals(r.getJobId()))
                .filter(r -> trace.getRobotId().equals(r.getRobotId()))
                .collect(Collectors.toList());
        if(runsList.size() == 0) {
            trace.setStatus(JobStatus.READY.name());
            runRepository.save(trace);
            LOGGER.info("Start new job with UID: " + trace.getUid() + ", robot id: " + trace.getRobotId());
        } else {
            trace = runsList.get(0);
            LOGGER.info("Check old job with UID: " + trace.getUid() + ", robot id: " + trace.getRobotId());
        }
        while(true) {
            RunEntity runFromBase = runRepository.findById(trace.getId()).get();
            if (runFromBase == null || JobStatus.SUCCESS.name().equals(runFromBase.getStatus()) || JobStatus.FAILURE.name().equals(runFromBase.getStatus())) {
                return;
            }
            Thread.sleep(checkTimeout);
        }
    }
}
