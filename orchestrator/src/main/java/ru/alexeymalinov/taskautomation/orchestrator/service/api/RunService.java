package ru.alexeymalinov.taskautomation.orchestrator.service.api;

import org.springframework.stereotype.Service;
import ru.alexeymalinov.taskautomation.core.model.Job;
import ru.alexeymalinov.taskautomation.core.model.JobStatus;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.RunEntity;

import java.util.List;

@Service
public interface RunService {

    List<RunEntity> findRunLogsByRobotIdAndJobStatus(Integer robotId, JobStatus status);

    void setStatusToRunLogs(List<RunEntity> runLogs, JobStatus jobStatus);

    List<Job> getJobsByRunLogs(List<RunEntity> runLogs);

    void addJobResult(Job Job);
}

