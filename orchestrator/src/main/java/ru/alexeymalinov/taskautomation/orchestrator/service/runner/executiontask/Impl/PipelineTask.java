package ru.alexeymalinov.taskautomation.orchestrator.service.runner.executiontask.Impl;

import ru.alexeymalinov.taskautomation.orchestrator.db.entity.RunEntity;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.StageEntity;
import ru.alexeymalinov.taskautomation.orchestrator.service.runner.RunnerService;
import ru.alexeymalinov.taskautomation.orchestrator.service.runner.executiontask.ExecutionTask;


public class PipelineTask implements ExecutionTask {

    private final StageEntity stage;
    private final RunEntity trace;
    private final RunnerService service;

    public PipelineTask(StageEntity stage, RunEntity trace, RunnerService service) {
        this.stage = stage;
        this.trace = trace;
        this.service = service;
    }

    @Override
    public void run() {
        service.run(stage, trace);
    }

}
