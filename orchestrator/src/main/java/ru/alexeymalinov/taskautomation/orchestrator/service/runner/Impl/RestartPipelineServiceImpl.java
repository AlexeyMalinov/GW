package ru.alexeymalinov.taskautomation.orchestrator.service.runner.Impl;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.apache.catalina.Pipeline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import ru.alexeymalinov.taskautomation.core.model.JobStatus;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.PipelineEntity;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.RunEntity;
import ru.alexeymalinov.taskautomation.orchestrator.db.repository.PipelineRepository;
import ru.alexeymalinov.taskautomation.orchestrator.db.repository.RunRepository;
import ru.alexeymalinov.taskautomation.orchestrator.service.runner.RestartPipelineService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringComponent
public class RestartPipelineServiceImpl implements RestartPipelineService {

    private final Logger LOGGER = LoggerFactory.getLogger(RestartPipelineService.class);
    private final RunRepository runRepository;
    private final PipelineRepository pipelineRepository;
    private final PipelineRunnerService pipelineRunnerService;

    public RestartPipelineServiceImpl(RunRepository runRepository, PipelineRepository pipelineRepository, PipelineRunnerService pipelineRunnerService) {
        this.runRepository = runRepository;
        this.pipelineRepository = pipelineRepository;
        this.pipelineRunnerService = pipelineRunnerService;
    }

    @Override
    public void restartPipelines() {
        Map<String, RunEntity> runsMap = findNotFinishedPipeline();
        for (String uid : runsMap.keySet()) {
            PipelineEntity pipelineEntity = pipelineRepository.findById(runsMap.get(uid).getPipelineId()).get();
            LOGGER.info("Restart pipeline: id: " + pipelineEntity.getId() + "UID: "+ uid );
            pipelineRunnerService.run(pipelineEntity, runsMap.get(uid));
        }
    }

    private Map<String, RunEntity> findNotFinishedPipeline() {
        Map<String, RunEntity> runsMap = new HashMap<>();
        List<RunEntity> startedPipeline = findStartedPipeline();
        for (RunEntity runEntity : startedPipeline) {
            runsMap.put(runEntity.getUid(), runEntity);
        }
        return runsMap;
    }

    private List<RunEntity> findStartedPipeline() {
        List<RunEntity> startedPipeline = runRepository.findByStatus(JobStatus.READY.name());
        startedPipeline.addAll(runRepository.findByStatus(JobStatus.STARTED.name()));
        return startedPipeline;
    }

}
