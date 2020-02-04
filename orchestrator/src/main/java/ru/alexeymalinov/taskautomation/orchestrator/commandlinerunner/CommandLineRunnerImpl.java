package ru.alexeymalinov.taskautomation.orchestrator.commandlinerunner;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.boot.CommandLineRunner;
import ru.alexeymalinov.taskautomation.orchestrator.service.runner.RestartPipelineService;

@SpringComponent
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final RestartPipelineService restartPipelineService;

    public CommandLineRunnerImpl(RestartPipelineService restartPipelineService) {
        this.restartPipelineService = restartPipelineService;
    }

    @Override
    public void run(String... args) throws Exception {
        restartPipelineService.restartPipelines();
    }
}
