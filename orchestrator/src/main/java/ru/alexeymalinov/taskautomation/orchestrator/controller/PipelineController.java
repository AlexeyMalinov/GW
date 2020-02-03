package ru.alexeymalinov.taskautomation.orchestrator.controller;

import org.springframework.web.bind.annotation.RestController;
import ru.alexeymalinov.taskautomation.orchestrator.service.api.PipelineService;

@RestController
public class PipelineController {

    PipelineService pipelineService;

    public PipelineController(PipelineService pipelineService) {
        this.pipelineService = pipelineService;
    }

}
