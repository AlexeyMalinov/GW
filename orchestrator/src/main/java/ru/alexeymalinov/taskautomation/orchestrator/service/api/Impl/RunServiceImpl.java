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
import java.util.stream.Collectors;

@Service
public class RunServiceImpl implements RunService {

    private final RunRepository runRepository;
    private final JobRepository jobRepository;

    public RunServiceImpl(RunRepository repository, JobRepository jobRepository) {
        this.runRepository = repository;
        this.jobRepository = jobRepository;
    }

    @Override
    public List<Job> getJobsByRunLogs(List<RunEntity> runLogs) {
        List<Job> jobs = new ArrayList<>();
        for (RunEntity runEntity : runLogs) {
            jobs.add(buildJob(runEntity));
        }
        return jobs;
    }

    public List<RunEntity> findRunLogsByRobotIdAndJobStatus(Integer robotId, JobStatus status){
        return runRepository
                .findByRobotId(robotId)
                .stream()
                .filter(r -> status.name().equals(r.getStatus()))
                .collect(Collectors.toList());
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


    @Override
    public void setStatusToRunLogs(List<RunEntity> runLogs, JobStatus jobStatus){
        for (RunEntity runLog : runLogs) {
            runLog.setStatus(jobStatus.name());
            runRepository.save(runLog);
        }
    }

    private Job buildJob(RunEntity runLog) {
        JobEntity jobEntity = jobRepository.findById(runLog.getJobId()).get();
        Job job = new Job(runLog.getUid(), runLog.getJobId(), runLog.getRobotId(), jobEntity.getName(), jobEntity.getTaskName(), jobEntity.getRepositoryUrl(), jobEntity.getStartTime(), jobEntity.getCount(), 0, TimeUnit.SECONDS, runLog.getStatus());
        return job;
    }
}
