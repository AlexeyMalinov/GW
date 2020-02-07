package ru.alexeymalinov.taskautomation.orchestrator.controller;

import org.springframework.web.bind.annotation.*;
import ru.alexeymalinov.taskautomation.core.model.Job;
import ru.alexeymalinov.taskautomation.core.model.JobStatus;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.RunEntity;
import ru.alexeymalinov.taskautomation.orchestrator.service.api.RunService;

import java.util.List;

@RestController
public class RunController {

    private final RunService runService;

    public RunController(RunService runService) {
        this.runService = runService;
    }

    @GetMapping("/robots/jobs/new/get/{robotsId}")
    private List<Job> getNewJobs(@PathVariable("robotsId") Integer robotId) {
        List<RunEntity> runLogs = runService.findRunLogsByRobotIdAndJobStatus(robotId, JobStatus.READY);
        runService.setStatusToRunLogs(runLogs, JobStatus.STARTED);
        return runService.getJobsByRunLogs(runLogs);
    }

    @GetMapping("/robots/jobs/old/get/{robotsId}")
    private List<Job> getOldJobs(@PathVariable("robotsId") Integer robotId) {
        List<RunEntity> runLogs = runService.findRunLogsByRobotIdAndJobStatus(robotId, JobStatus.STARTED);
        return runService.getJobsByRunLogs(runLogs);
    }

    @PostMapping("/robots/jobs/status/add")
    public void setJobStatus(@RequestBody Job job) {
        runService.addJobResult(job);
    }
}
