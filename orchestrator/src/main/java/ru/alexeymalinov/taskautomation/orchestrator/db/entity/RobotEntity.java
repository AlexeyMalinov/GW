package ru.alexeymalinov.taskautomation.orchestrator.db.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    @ManyToOne
    @JoinColumn(name = "group_id")
    private RobotsGroupEntity robotsGroup;

    public RobotEntity(RobotsGroupEntity robotsGroup) {
        this.robotsGroup = robotsGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RobotEntity that = (RobotEntity) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(robotsGroup, that.robotsGroup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, robotsGroup);
    }
}
