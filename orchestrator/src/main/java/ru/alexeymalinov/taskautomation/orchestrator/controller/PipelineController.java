package ru.alexeymalinov.taskautomation.orchestrator.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.PipelineEntity;
import ru.alexeymalinov.taskautomation.orchestrator.service.PipelineService;

import java.util.List;

@RestController
public class PipelineController {

    PipelineService pipelineService;

    public PipelineController(PipelineService pipelineService) {
        this.pipelineService = pipelineService;
    }

    @GetMapping("/api/pipelines")
    public List<PipelineEntity> allPipeline(){
        return pipelineService.getAllPipelines();
    }
}
