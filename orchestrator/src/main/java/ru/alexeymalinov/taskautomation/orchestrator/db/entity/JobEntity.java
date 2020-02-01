package ru.alexeymalinov.taskautomation.orchestrator.db.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
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

}
