package ru.alexeymalinov.taskautomation.repository.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alexeymalinov.taskautomation.repository.db.entity.TaskEntity;

@Repository
public interface TaskRepository extends JpaRepository <TaskEntity, Integer> {
    TaskEntity findByName(String name);
}
