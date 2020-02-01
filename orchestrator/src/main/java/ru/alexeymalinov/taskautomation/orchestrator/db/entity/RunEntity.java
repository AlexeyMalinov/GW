package ru.alexeymalinov.taskautomation.orchestrator.db.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "run")
public class RunEntity {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @SequenceGenerator(name = "run_id_gen", sequenceName = "run_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "run_id_gen")
    private Integer id;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "pipeline_id", nullable = false)
    private Integer pipelineId;

    @Column(name = "stage_id", nullable = false)
    private Integer stageId;

    @Column(name = "job_id", nullable = false)
    private Integer jobId;
}
