package ru.alexeymalinov.taskautomation.orchestrator.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.RobotEntity;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.RobotsGroupEntity;

import java.util.List;

@Repository
public interface RobotRepository extends JpaRepository<RobotEntity, Integer> {

    List<RobotEntity> findByRobotsGroup(RobotsGroupEntity robotsGroupEntity);

    RobotEntity findByNameAndRobotsGroup(String name, RobotsGroupEntity robotsGroupEntity);

}
