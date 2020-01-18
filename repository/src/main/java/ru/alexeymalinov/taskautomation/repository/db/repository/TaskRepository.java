package ru.alexeymalinov.taskautomation.repository.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alexeymalinov.taskautomation.repository.db.entity.TaskEntity;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository <TaskEntity, Integer> {
    // https://docs.spring.io/spring-data/jpa/docs/2.2.3.RELEASE/reference/html/#jpa.query-methods.query-creation
    TaskEntity findByName(String name);
}
