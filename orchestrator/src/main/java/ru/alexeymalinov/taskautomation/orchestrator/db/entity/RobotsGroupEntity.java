package ru.alexeymalinov.taskautomation.orchestrator.db.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "robots_group")
public class RobotsGroupEntity {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @SequenceGenerator(name = "robots_group_id_gen", sequenceName = "robots_group_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "robots_group_id_gen")
    private Integer id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "robot_id")
    private Integer robotId;
}
