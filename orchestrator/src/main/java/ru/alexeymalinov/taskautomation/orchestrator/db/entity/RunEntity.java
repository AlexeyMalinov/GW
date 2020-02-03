package ru.alexeymalinov.taskautomation.orchestrator.db.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Builder
@Table(name = "run")
public class RunEntity implements Cloneable{

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @SequenceGenerator(name = "run_id_gen", sequenceName = "run_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "run_id_gen")
    private Integer id;

    @Column(name = "uid", nullable = false)
    private String uid;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "pipeline_id", nullable = false)
    private Integer pipelineId;

    @Column(name = "stage_id", nullable = false)
    private Integer stageId;

    @Column(name = "job_id", nullable = false)
    private Integer jobId;

    @Column(name = "robot_id", nullable = false)
    private Integer robotId;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "description")
    private String description;

    @Override
    public RunEntity clone() throws CloneNotSupportedException {
        return new RunEntity(id, uid, startTime, pipelineId, stageId, jobId, robotId, status, description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RunEntity runEntity = (RunEntity) o;
        return Objects.equals(id, runEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "RunEntity{" +
                "id=" + id +
                ", uid='" + uid + '\'' +
                ", startTime=" + startTime +
                ", pipelineId=" + pipelineId +
                ", stageId=" + stageId +
                ", jobId=" + jobId +
                ", robotId=" + robotId +
                ", status='" + status + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
