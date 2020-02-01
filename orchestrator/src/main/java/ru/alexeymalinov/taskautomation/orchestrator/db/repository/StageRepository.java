package ru.alexeymalinov.taskautomation.orchestrator.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.PipelineEntity;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.StageEntity;

import java.util.List;

@Repository
public interface StageRepository extends JpaRepository <StageEntity, Integer> {
    List<StageEntity> findByPipeline(PipelineEntity pipeline);
}
