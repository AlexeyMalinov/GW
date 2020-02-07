package ru.alexeymalinov.taskautomation.robot.connection;

import ru.alexeymalinov.taskautomation.core.model.Job;
import ru.alexeymalinov.taskautomation.core.model.JobStatus;

import java.util.List;

public interface OrchestratorConnection {

    List<Job> getJobsByJobStatus(JobStatus jobStatus);

    void setJobStatus(Job job);

    static OrchestratorConnection getInstance(Integer robotId, String orchestratorUrl){
        return new OrchestratorConnectionImpl(robotId, orchestratorUrl);
    }
}
