package ru.alexeymalinov.taskautomation.orchestrator.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.JobEntity;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.StageEntity;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository <JobEntity, Integer> {

    JobEntity findByNameAndStage(String name, StageEntity stageEntity);
    List<JobEntity> findAllByStage(StageEntity stageEntity);
}
