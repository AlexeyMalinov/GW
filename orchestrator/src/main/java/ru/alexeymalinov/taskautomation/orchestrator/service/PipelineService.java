package ru.alexeymalinov.taskautomation.orchestrator.service;

import org.springframework.stereotype.Service;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.PipelineEntity;

import java.util.List;

@Service
public interface PipelineService {
    List<PipelineEntity> getAllPipelines();
}
