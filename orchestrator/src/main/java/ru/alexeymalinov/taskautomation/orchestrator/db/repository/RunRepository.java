package ru.alexeymalinov.taskautomation.orchestrator.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.RunEntity;

import java.util.List;

@Repository
public interface RunRepository extends JpaRepository<RunEntity, Integer> {
    List<RunEntity> findByPipelineId(Integer id);
}
