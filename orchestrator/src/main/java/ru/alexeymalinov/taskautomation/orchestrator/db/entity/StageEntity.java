package ru.alexeymalinov.taskautomation.orchestrator.db.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stage")
public class StageEntity implements PipelineElement{

    @Id
    @Column(name="id", unique = true, nullable = false)
    @SequenceGenerator(name = "stage_id_gen", sequenceName = "stage_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "stage_id_gen")
    private Integer id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "pipeline_id", nullable = false)
    private PipelineEntity pipeline;

    @OneToMany(mappedBy = "stage", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Set<JobEntity> jobs;

    @Column(name = "previous_stage_id")
    private Integer previousStageId;

    @Column(name = "next_stage_id")
    private Integer nextStageId;

    public StageEntity(PipelineEntity pipeline) {
        this.pipeline = pipeline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StageEntity that = (StageEntity) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(pipeline, that.pipeline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, pipeline);
    }
}
