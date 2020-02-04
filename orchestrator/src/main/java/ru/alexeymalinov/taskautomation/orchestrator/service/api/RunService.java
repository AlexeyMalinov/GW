package ru.alexeymalinov.taskautomation.orchestrator.service.api;

import org.springframework.stereotype.Service;
import ru.alexeymalinov.taskautomation.core.model.Job;

import java.util.List;

@Service
public interface RunService {

    List<Job> getJob(Integer robotId);
    void addJobResult(Job Job);
}

