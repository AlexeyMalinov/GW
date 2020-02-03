package ru.alexeymalinov.taskautomation.orchestrator.service.runner.executiontask.Impl;

import ru.alexeymalinov.taskautomation.orchestrator.db.entity.JobEntity;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.RunEntity;
import ru.alexeymalinov.taskautomation.orchestrator.service.runner.RunnerService;
import ru.alexeymalinov.taskautomation.orchestrator.service.runner.executiontask.ExecutionTask;

public class StageTask implements ExecutionTask {

    private final JobEntity job;
    private final RunEntity trace;
    private final RunnerService service;

    public StageTask(JobEntity job, RunEntity trace, RunnerService service) {
        this.job = job;
        this.trace = trace;
        this.service = service;
    }

    @Override
    public void run() {
        service.run(job, trace);
    }
}
