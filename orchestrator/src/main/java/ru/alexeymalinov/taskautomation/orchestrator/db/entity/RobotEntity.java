package ru.alexeymalinov.taskautomation.orchestrator.db.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "robot")
public class RobotEntity {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @SequenceGenerator(name = "robot_id_gen", sequenceName = "robot_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "robot_id_gen")
    private Integer id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

}
