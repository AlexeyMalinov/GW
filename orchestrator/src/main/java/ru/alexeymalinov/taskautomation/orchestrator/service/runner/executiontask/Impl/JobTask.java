package ru.alexeymalinov.taskautomation.orchestrator.service.runner.executiontask.Impl;

import lombok.SneakyThrows;
import ru.alexeymalinov.taskautomation.core.model.JobStatus;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.RobotEntity;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.RunEntity;
import ru.alexeymalinov.taskautomation.orchestrator.db.repository.RunRepository;
import ru.alexeymalinov.taskautomation.orchestrator.service.runner.executiontask.ExecutionTask;

public class JobTask implements ExecutionTask {

    private final int checkTimeout;
    private final RunRepository runRepository;
    private final RunEntity trace;
    private final RobotEntity robot;

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
        trace.setStatus(JobStatus.READY.name());
        runRepository.save(trace);
        while(true) {
            RunEntity runFromBase = runRepository.findById(trace.getId()).get();
            if (runFromBase == null || JobStatus.SUCCESS.name().equals(runFromBase.getStatus()) || JobStatus.FAILURE.name().equals(runFromBase.getStatus())) {
                return;
            }
            Thread.sleep(checkTimeout);
        }
    }
}
