package ru.alexeymalinov.taskautomation.orchestrator.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.PipelineEntity;

@Repository
public interface PipelineRepository extends JpaRepository<PipelineEntity, Integer> {
    // https://docs.spring.io/spring-data/jpa/docs/2.2.3.RELEASE/reference/html/#jpa.query-methods.query-creation
    PipelineEntity findByName(String name);
}
