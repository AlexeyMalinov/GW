package ru.alexeymalinov.taskautomation.orchestrator.service.runner;

import ru.alexeymalinov.taskautomation.orchestrator.db.entity.*;

public interface RunnerService {

    void run(PipelineElement element, RunEntity trace);

}
