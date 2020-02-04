package ru.alexeymalinov.taskautomation.orchestrator.service.api.Impl;

import org.springframework.stereotype.Service;
import ru.alexeymalinov.taskautomation.core.model.Job;
import ru.alexeymalinov.taskautomation.core.model.JobStatus;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.JobEntity;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.RunEntity;
import ru.alexeymalinov.taskautomation.orchestrator.db.repository.JobRepository;
import ru.alexeymalinov.taskautomation.orchestrator.db.repository.RunRepository;
import ru.alexeymalinov.taskautomation.orchestrator.service.api.RunService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RunServiceImpl implements RunService {

    private final RunRepository runRepository;
    private final JobRepository jobRepository;

    public RunServiceImpl(RunRepository repository, JobRepository jobRepository) {
        this.runRepository = repository;
        this.jobRepository = jobRepository;
    }

    @Override
    public List<Job> getJob(Integer robotId) {
        List<RunEntity> runLogs = runRepository.findByRobotId(robotId);
        List<Job> jobs = new ArrayList<>();
        for (RunEntity runEntity : runLogs) {
            jobs.add(buildJob(runEntity));
        }
        return jobs;
    }

    @Override
    public void addJobResult(Job job) {
        List<RunEntity> runLog = runRepository.findByUid(job.getUid());
        RunEntity runResult = runLog.stream()
                .filter(r -> job.getRobotId().equals(r.getRobotId()))
                .filter(r -> job.getId().equals(r.getJobId()))
                .findFirst()
                .get();
        runResult.setStatus(job.getStatus());
        runRepository.save(runResult);
    }

    private Job buildJob(RunEntity runLog){
        JobEntity jobEntity = jobRepository.findById(runLog.getJobId()).get();
        runLog.setStatus(JobStatus.STARTED.name());
        Job job = new Job(runLog.getUid(), runLog.getJobId(), runLog.getRobotId(),jobEntity.getName(),jobEntity.getTaskName(),jobEntity.getRepositoryUrl(),jobEntity.getStartTime(),jobEntity.getCount(),0, TimeUnit.SECONDS, runLog.getStatus());
        runRepository.save(runLog);
        return job;
    }
}
