package ru.alexeymalinov.taskautomation.orchestrator.controller;

import org.springframework.web.bind.annotation.*;
import ru.alexeymalinov.taskautomation.core.model.Job;
import ru.alexeymalinov.taskautomation.orchestrator.service.api.RunService;

import java.util.List;

@RestController
public class RunController {

    private final RunService runService;

    public RunController(RunService runService) {
        this.runService = runService;
    }

    @GetMapping("/robots/jobs/get/{robotsId}")
    private List<Job> getJobs(@PathVariable("robotsId") Integer robotId){
        return runService.getJob(robotId);
    }

    @PostMapping("/robots/jobs/status/add")
    public void setJobStatus(@RequestBody Job job){
        runService.addJobResult(job);
    }
}
