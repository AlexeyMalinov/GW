package ru.alexeymalinov.taskautomation.orchestrator.db.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "job")
public class JobEntity {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @SequenceGenerator(name = "job_id_gen", sequenceName = "job_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "job_id_gen")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "task_name", nullable = false)
    private String taskName;

    @Column(name = "repository_url", nullable = false)
    private String repositoryUrl;

    @Column(name = "robots_group_id", nullable = false)
    private Integer robotsGroupId;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "count")
    private Integer count;

    @ManyToOne
    @JoinColumn(name = "stage_id", nullable = false)
    private StageEntity stage;

    public JobEntity(StageEntity stage) {
        this.stage = stage;
    }

    public String getName() {
        return name;
    }

    public StageEntity getStage() {
        return stage;
    }

    public Integer getId() {
        return id;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobEntity jobEntity = (JobEntity) o;
        return Objects.equals(name, jobEntity.name) &&
                Objects.equals(stage, jobEntity.stage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, stage);
    }
}
