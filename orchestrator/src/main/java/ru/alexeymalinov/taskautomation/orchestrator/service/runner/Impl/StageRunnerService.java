package ru.alexeymalinov.taskautomation.orchestrator.service.runner.Impl;

import com.vaadin.flow.spring.annotation.SpringComponent;
import lombok.Getter;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.*;
import ru.alexeymalinov.taskautomation.orchestrator.db.repository.StageRepository;
import ru.alexeymalinov.taskautomation.orchestrator.service.runner.RunnerService;
import ru.alexeymalinov.taskautomation.orchestrator.service.runner.executiontask.ExecutionTask;
import ru.alexeymalinov.taskautomation.orchestrator.service.runner.executiontask.Impl.StageTask;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Getter
@SpringComponent
public class StageRunnerService implements RunnerService {

    private final Logger LOGGER = LoggerFactory.getLogger(StageRunnerService.class);
    private final StageRepository stageRepository;
    private final JobRunnerService jobRunnerService;

    public StageRunnerService(StageRepository stageRepository, JobRunnerService jobRunnerService) {
        this.stageRepository = stageRepository;
        this.jobRunnerService = jobRunnerService;
    }

    @SneakyThrows
    @Override
    public void run(PipelineElement element, RunEntity trace) {
        RunEntity currentTrace = trace.clone();
        StageEntity stage;
        if (element instanceof StageEntity) {
            stage = (StageEntity) element;
        } else {
            LOGGER.info("invalid pipeline element format", new IllegalArgumentException("invalid pipeline element format"));
            return;
        }
        LOGGER.info("Start stage name: " + stage.getName() + ", id: " + stage.getId());

        if (currentTrace == null) return;
        currentTrace.setStageId(stage.getId());
        Set<JobEntity> jobs = stage.getJobs();
        runJob(jobs, currentTrace);
    }

    @SneakyThrows
    private void runJob(Set<JobEntity> jobs, RunEntity trace) {
        ExecutorService executorService = Executors.newFixedThreadPool(jobs.size());
        try {
            for (JobEntity job : jobs) {
                ExecutionTask task = new StageTask(job, trace.clone(), jobRunnerService);
                executorService.submit(task);
            }
            boolean isFinished = false;
            while (!isFinished){
                isFinished = executorService.awaitTermination(10, TimeUnit.SECONDS);
            }
        } finally {
            executorService.shutdown();
        }
    }
}
