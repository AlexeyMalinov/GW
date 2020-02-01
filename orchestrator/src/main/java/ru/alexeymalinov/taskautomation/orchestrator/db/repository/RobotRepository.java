package ru.alexeymalinov.taskautomation.orchestrator.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.RobotEntity;

@Repository
public interface RobotRepository extends JpaRepository<RobotEntity, Integer> {
}
