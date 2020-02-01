package ru.alexeymalinov.taskautomation.orchestrator.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.PipelineEntity;
import ru.alexeymalinov.taskautomation.orchestrator.db.repository.PipelineRepository;
import ru.alexeymalinov.taskautomation.orchestrator.service.PipelineService;

import java.util.List;

@Service
public class PipelineServiceImpl implements PipelineService {

    private final PipelineRepository repository;

    public PipelineServiceImpl(PipelineRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public List<PipelineEntity> getAllPipelines() {
        return repository.findAll();
    }
}
