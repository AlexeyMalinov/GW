package ru.alexeymalinov.taskautomation.orchestrator.service.runner.Impl;

import com.fasterxml.jackson.databind.ser.impl.StringArraySerializer;
import com.vaadin.flow.spring.annotation.SpringComponent;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.*;
import ru.alexeymalinov.taskautomation.orchestrator.db.repository.StageRepository;
import ru.alexeymalinov.taskautomation.orchestrator.service.runner.RunnerService;
import ru.alexeymalinov.taskautomation.orchestrator.service.runner.executiontask.ExecutionTask;
import ru.alexeymalinov.taskautomation.orchestrator.service.runner.executiontask.Impl.PipelineTask;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
@SpringComponent
public class PipelineRunnerService implements RunnerService {

    private final Logger LOGGER = LoggerFactory.getLogger(PipelineRunnerService.class);
    private final StageRepository stageRepository;
    private final StageRunnerService stageRunnerService;

    public PipelineRunnerService(StageRepository stageRepository, StageRunnerService stageRunnerService) {
        this.stageRepository = stageRepository;
        this.stageRunnerService = stageRunnerService;
    }

    @Override
    public void run(PipelineElement element, RunEntity trace) {
        PipelineEntity pipelineEntity;
        if (element instanceof PipelineEntity) {
            pipelineEntity = (PipelineEntity) element;
        } else {
            LOGGER.info("invalid pipeline element format", new IllegalArgumentException("invalid pipeline element format"));
            return;
        }
        LOGGER.info("Start pipeline with UID: " + trace.getUid());
        RunEntity.RunEntityBuilder builder = RunEntity.builder();
        builder.uid(trace.getUid() == null?UUID.randomUUID().toString():trace.getUid());
        builder.pipelineId(pipelineEntity.getId());

        trace = builder.build();
        StageEntity stage = pipelineEntity.getStages().stream().filter(n -> n.getPreviousStageId() == null).findFirst().get();

        runStage(stage, trace);

    }

    @SneakyThrows
    private void runStage(StageEntity stage, RunEntity trace) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        try {
            while (stage != null) {
                ExecutionTask task = new PipelineTask(stage, trace, stageRunnerService);
                executorService.submit(task);
                stage = getNexStage(stage);
            }
        } finally {
            executorService.shutdown();
        }
    }

    private StageEntity getNexStage(StageEntity stageEntity) {
        if (stageEntity.getNextStageId() == null) return null;
        return stageRepository.findById(stageEntity.getNextStageId()).get();
    }
}
