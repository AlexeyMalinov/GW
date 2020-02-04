package ru.alexeymalinov.taskautomation.orchestrator.service.runner;

import com.vaadin.flow.spring.annotation.SpringComponent;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.PipelineEntity;

@SpringComponent
public interface RestartPipelineService {
    void restartPipelines();
}
