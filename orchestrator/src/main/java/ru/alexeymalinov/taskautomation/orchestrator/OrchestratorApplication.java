package ru.alexeymalinov.taskautomation.orchestrator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.alexeymalinov.taskautomation.orchestrator.service.runner.RestartPipelineService;

@SpringBootApplication
public class OrchestratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrchestratorApplication.class, args);
	}


}
