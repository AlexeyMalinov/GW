package ru.alexeymalinov.taskautomation.orchestrator.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.RobotsGroupEntity;

@Repository
public interface RobotsGroupRepository extends JpaRepository<RobotsGroupEntity, Integer> {
    RobotsGroupEntity findByName(String name);
}
